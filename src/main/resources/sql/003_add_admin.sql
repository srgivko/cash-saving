begin transaction;
insert into users(id, account_expired, account_locked, activationcode, credentials_expired, email, enabled, password,
                  username)
VALUES (1, false, false, null, false, null, true, crypt('123', gen_salt('bf', 12)), 'admin');
insert into user_authority(user_id, authority_id)
VALUES (1, 1),
       (1, 2);


end transaction;