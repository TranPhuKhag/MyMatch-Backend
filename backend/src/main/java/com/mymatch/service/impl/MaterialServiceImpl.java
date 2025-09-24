package com.mymatch.service.impl;

import com.mymatch.dto.request.material.MaterialCreationRequest;
import com.mymatch.dto.request.material.MaterialFilter;
import com.mymatch.dto.request.material.MaterialUpdateRequest;
import com.mymatch.dto.request.wallet.WalletRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.filemanager.FileDownloadResponse;
import com.mymatch.dto.response.material.MaterialResponse;
import com.mymatch.dto.response.transaction.TransactionResponse;
import com.mymatch.entity.*;
import com.mymatch.enums.StorageType;
import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionType;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.MaterialMapper;
import com.mymatch.repository.*;
import com.mymatch.service.FileManagerService;
import com.mymatch.service.MaterialService;
import com.mymatch.service.TransactionService;
import com.mymatch.service.WalletService;
import com.mymatch.specification.MaterialSpecification;
import com.mymatch.utils.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialServiceImpl implements MaterialService {
    Long COIN_PER_MATERIAL = 5L;
    MaterialRepository materialRepository;
    MaterialPurchaseRepository materialPurchaseRepository;
    FileManagerService fileManagerService;
    WalletService walletService;
    TransactionService transactionService;
    MaterialMapper materialMapper;
    CourseRepository courseRepository;
    LecturerRepository lecturerRepository;
    UserRepository userRepository;


    @Override
    public MaterialResponse createMaterial(MaterialCreationRequest request) throws Exception {
        Double fileSize = getFileSizeInMB(request.getFile());
        if (fileSize > 50.0) {
            throw new AppException(ErrorCode.FILE_SIZE_EXCEEDED);
        }
        Material material = materialMapper.toMaterial(request);
        if (request.getCourseId() != null) {
            Course course = courseRepository.findById(request.getCourseId()).orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
            material.setCourse(course);
        }
        if (request.getLecturerId() != null) {
            Lecturer lecturer = lecturerRepository.findById(request.getLecturerId()).orElseThrow(() -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
            material.setLecturer(lecturer);
        }
        Long currentUserId = SecurityUtil.getCurrentUserId();
        User owner = userRepository.findById(currentUserId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String uuid = java.util.UUID.randomUUID().toString();
        String fileUrl = fileManagerService.save(request.getFile(), buildFilePath(currentUserId, uuid), StorageType.PRIVATE);
        material.setSize(fileSize);
        material.setFileURL(fileUrl);
        material.setOwner(owner);
        material.setFileType(request.getFile().getContentType());
        material.setOriginalFileName(request.getFile().getOriginalFilename());
        material.setPrice(COIN_PER_MATERIAL);
        material = materialRepository.save(material);
        return materialMapper.toMaterialResponse(material);
    }

    @Override
    public MaterialResponse getMaterialById(Long id) {
        Material material = materialRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));
        boolean isPurchased = materialPurchaseRepository.existsByMaterial_IdAndAndBuyer_Id(id, SecurityUtil.getCurrentUserId());
        User currentUser = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (currentUser.getId().equals(material.getOwner().getId())) {
            isPurchased = true;
        }
        MaterialResponse materialResponse = materialMapper.toMaterialResponse(material);
        materialResponse.setIsPurchased(isPurchased);
        return materialResponse;
    }

    @Override
    public MaterialResponse updateMaterial(Long id, MaterialUpdateRequest request) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));
        Long currentUserId = SecurityUtil.getCurrentUserId();
        if (!Objects.equals(material.getOwner().getId(), currentUserId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        materialMapper.updateMaterial(material, request);

        if (request.getCourseId() != null) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
            material.setCourse(course);
        }

        if (request.getLecturerId() != null) {
            Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                    .orElseThrow(() -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
            material.setLecturer(lecturer);
        }

        material = materialRepository.save(material);
        return materialMapper.toMaterialResponse(material);
    }

    @Override
    @Transactional
    public MaterialResponse purchaseMaterial(Long materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));
        Long currentUserId = SecurityUtil.getCurrentUserId();
        if (currentUserId.equals(material.getOwner().getId())) {
            throw new AppException(ErrorCode.CANNOT_PURCHASE_OWN_MATERIAL);
        }
        boolean alreadyPurchased = materialPurchaseRepository
                .existsByMaterial_IdAndAndBuyer_Id(materialId, currentUserId);
//        if (alreadyPurchased) {
//            throw new AppException(ErrorCode.MATERIAL_ALREADY_PURCHASED);
//        }
        Transaction deductTransaction = null;
        Transaction rewardTransaction = null;
        try {

            WalletRequest deductRequest = WalletRequest.builder()
                    .type(TransactionType.OUT)
                    .userId(currentUserId)
                    .source(TransactionSource.SERVICE_PURCHASE)
                    .coin(material.getPrice())
                    .description("Purchase material: " + material.getName())
                    .build();
            WalletRequest rewardRequest = WalletRequest.builder()
                    .type(TransactionType.IN)
                    .userId(material.getOwner().getId())
                    .source(TransactionSource.REWARD)
                    .coin(material.getPrice() * 90 / 100) // Owner gets 90% of the price
                    .description("Sale of material: " + material.getName())
                    .build();
            deductTransaction = walletService.deductFromWallet(deductRequest);
            rewardTransaction = walletService.addToCoinWallet(rewardRequest);
            material.setPurchaseCount(material.getPurchaseCount() + 1);
            material = materialRepository.save(material);
            MaterialPurchase purchase = MaterialPurchase.builder()
                    .material(material)
                    .totalCoin(material.getPrice())
                    .transactionCode(deductTransaction.getTransactionCode())
                    .platformFee(material.getPrice() * 10 / 100)
                    .ownerEarning(material.getPrice() * 90 / 100)
                    .buyer(userRepository.findById(currentUserId)
                            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)))
                    .build();
            materialPurchaseRepository.save(purchase);
        } catch (Exception e) {
            transactionService.rollbackTransaction(deductTransaction, "Purchase material failed: " + e.getMessage());
            transactionService.rollbackTransaction(rewardTransaction, "Reward coin failed " + material.getName());
            throw e;
        }
        return materialMapper.toMaterialResponse(material);
    }

    @Override
    public PageResponse<MaterialResponse> getAllMaterials(MaterialFilter filter, int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page - 1, size,
                Sort.by(Sort.Direction.fromString(sortDir), sortBy));

        Specification<Material> spec = MaterialSpecification.withFilter(filter);
        Page<Material> materialPage = materialRepository.findAll(spec, pageable);

        Long currentUserId = SecurityUtil.getCurrentUserId();
        List<MaterialResponse> responses = materialPage.getContent().stream()
                .map(material -> {
                    MaterialResponse response = materialMapper.toMaterialResponse(material);
                    boolean isPurchased = materialPurchaseRepository
                            .existsByMaterial_IdAndAndBuyer_Id(material.getId(), currentUserId);
                    if (Objects.equals(material.getOwner().getId(), currentUserId)) {
                        isPurchased = true;
                    }
                    response.setIsPurchased(isPurchased);
                    return response;
                })
                .toList();

        return PageResponse.<MaterialResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(materialPage.getTotalElements())
                .totalPages(materialPage.getTotalPages())
                .data(responses)
                .build();
    }

    @Override
    public FileDownloadResponse downloadMaterial(Long materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));
        Long currentUserId = SecurityUtil.getCurrentUserId();
        boolean isPurchased = materialPurchaseRepository
                .existsByMaterial_IdAndAndBuyer_Id(materialId, currentUserId);
        if (currentUserId.equals(material.getOwner().getId())) {
            isPurchased = true;
        }
        if (!isPurchased) {
            throw new AppException(ErrorCode.MATERIAL_NOT_PURCHASED);
        }

        return FileDownloadResponse.builder()
                .contentType(material.getFileType())
                .fileName(material.getOriginalFileName())
                .nginxPath(material.getFileURL())
                .build();

    }

    @Override
    public void deleteMaterial(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));

        Long currentUserId = SecurityUtil.getCurrentUserId();
        if (!Objects.equals(material.getOwner().getId(), currentUserId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        materialRepository.delete(material);
    }

    private String buildFilePath(Long userId, String prefix) {

        return userId.toString() + "/" + prefix;
    }

    private Double getFileSizeInMB(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("File is empty");
        }
        return file.getSize() / (1024.0 * 1024.0);
    }


}
