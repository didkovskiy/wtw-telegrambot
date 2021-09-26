ALTER TABLE tg_user
    ADD PRIMARY KEY (chat_id);

DROP TABLE IF EXISTS wtw_list;
DROP TABLE IF EXISTS wtw_list_x_user;

CREATE TABLE wtw_list
(
    id          VARCHAR(20),
    title       VARCHAR(100),
    description VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE wtw_list_x_user
(
    wtw_list_id VARCHAR(20)  NOT NULL,
    user_id     VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES tg_user (chat_id),
    FOREIGN KEY (wtw_list_id) REFERENCES wtw_list (id),
    UNIQUE (user_id, wtw_list_id)
);