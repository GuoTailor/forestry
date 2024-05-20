CREATE TABLE notifications (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               title VARCHAR(255) NOT NULL,
                               content TEXT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_notifications (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    user_id INT NOT NULL,
                                    notification_id INT NOT NULL,
                                    is_read BOOLEAN DEFAULT FALSE,
                                    FOREIGN KEY (notification_id) REFERENCES notifications(id)
);
