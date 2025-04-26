package com.instalab.services;

import com.instalab.entities.Notification;
import com.instalab.entities.UserModel;
import com.instalab.repositories.NotificationRepository;
import com.instalab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Notification sendNotification(UUID professorId, String message) {
        UserModel prof = userRepository.findById(professorId)
              .orElseThrow(() -> new RuntimeException("Professor not found"));
        Notification notification = new Notification(prof, message);
        return notificationRepository.save(notification);
    }

    public List<Notification> listForProfessor(UUID professorId) {
        return notificationRepository.findByProfessorUserId(professorId);
    }

    @Transactional
    public void markAsRead(UUID notificationId) {
        Notification notif = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notif.setRead(true);
        notificationRepository.save(notif);
    }
}
