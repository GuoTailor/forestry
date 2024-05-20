CREATE TABLE "public".notifications (
                               id serial4 PRIMARY KEY,
                               title VARCHAR(255) NOT NULL,
                               content TEXT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "public".user_notifications (
                                    id serial4 PRIMARY KEY,
                                    user_id INT NOT NULL,
                                    notification_id INT NOT NULL,
                                    is_read BOOLEAN DEFAULT FALSE,
                                    FOREIGN KEY (notification_id) REFERENCES notifications(id)
);
