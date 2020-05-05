INSERT INTO users(username) VALUES ("yaga");

INSERT INTO user_auths(user_id,email,hash) VALUES (1,"yaga@example.com","$pbkdf2-sha512$531$AJGxFyMsRFPDT0p6KiME8B.vPbUg62PziTeIipt2twQ$njA3JX4OrF2OnCIZkr7mn.Tn4FAQkk1a/eJuMzYXDm4");

INSERT INTO user_details(user_id,nickname) VALUES (1,"たいち");

INSERT INTO auth_tokens(user_id,token,expiry) VALUES (1,"token","2020-05-02 21:00:00");
