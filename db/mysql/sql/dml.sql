INSERT INTO users(username) VALUES ("yaga");

INSERT INTO user_auths(user_id,email,hash) VALUES (1,"yaga@example.com","password");

INSERT INTO user_details(user_id,nickname) VALUES (1,"たいち");

INSERT INTO auth_tokens(user_id,token,expiry) VALUES (1,"token","2020-05-02 21:00:00");
