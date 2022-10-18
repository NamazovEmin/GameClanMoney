DROP TABLE  clans cascade ;
DROP TABLE users cascade;
DROP TABLE transaction_reasons cascade ;
DROP TABLE actor cascade;
DROP TABLE transactions cascade ;

CREATE TABLE actor (
                       id bigserial PRIMARY KEY,
                       actor_type VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS clans (
                                     id bigserial PRIMARY KEY,
                                     name VARCHAR(25),
                                     balance bigserial,
                                     actor_id bigserial references actor (id),
                                     created_at  timestamp default current_timestamp,
                                     updated_at  timestamp default current_timestamp
);

CREATE TABLE IF NOT EXISTS users (
                                       id bigserial PRIMARY KEY,
                                       name VARCHAR(25),
                                       balance bigserial,
                                       clan_id bigserial references clans (id),
                                       actor_id bigserial references actor (id),
                                       created_at  timestamp default current_timestamp,
                                       updated_at  timestamp default current_timestamp
);
CREATE TABLE transaction_reasons (
                                     id bigserial PRIMARY KEY,
                                     name VARCHAR(25) unique
);



CREATE TABLE IF NOT EXISTS transactions (
                                                             id bigserial PRIMARY KEY,
                                                             reason VARCHAR(50) ,
                                                             sender bigserial references actor (id),
                                                             sender_balance_before bigserial,
                                                             sender_balance_after bigserial,
                                                             receiver bigserial references actor (id),
                                                             receiver_balance_before bigserial,
                                                             receiver_balance_after bigserial,
                                                             gold_transaction bigserial,
                                                             created_at  timestamp default current_timestamp,
                                                             updated_at  timestamp default current_timestamp
);


INSERT INTO transaction_reasons (name) VALUES
                                           ('TO_BANK_TRANSFER'),
                                           ('FROM_BANK_TRANSFER'),
                                           ('KILL_MONSTER'),
                                           ('Kill_BOSS'),
                                           ('PASSED_THE_DUNGEON'),
                                           ('QUEST_COMPLETED')
;


INSERT INTO Actor (actor_type) VALUES
                                      ('User'),
                                      ('Clan'),
                                      ('GlobalBank'),
                                      ('User'),
                                      ('User'),
                                      ('User'),
                                      ('User'),
                                      ('Clan')
;



insert into clans (name, balance,actor_id) values
                                               ('Russia', 500, 2),
                                               ('Turkey', 500, 8)
;

insert into users (name,balance, clan_id,actor_id) values
                                              ('Nastya', 500, 1, 1),
                                              ('Emin', 500, 2, 4),
                                              ('Tatyana', 500, 1, 5),
                                              ('Ura', 500, 2, 6),
                                              ('Aynur', 500, 1, 7)
;













