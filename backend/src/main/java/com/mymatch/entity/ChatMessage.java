package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE chat_message SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class ChatMessage extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name="conversation_id", nullable=false)
    Conversation conversation;

    @ManyToOne
    @JoinColumn(name="sender_id", nullable=false)
    Student sender;
//    @Enumerated(EnumType.STRING)
//    MessageType type = MessageType.TEXT;

    @Column(columnDefinition="TEXT")
    String message;

    String attachmentUrl;

    @ManyToOne
    @JoinColumn(name="reply_to_id")
    ChatMessage replyTo;

    Boolean edited = false;
    boolean me;
}
