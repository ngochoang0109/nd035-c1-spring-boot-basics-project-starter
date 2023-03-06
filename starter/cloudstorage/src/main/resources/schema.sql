CREATE TABLE IF NOT EXISTS postgres.superduperdrive.USERS (
  userid SERIAL PRIMARY KEY,
  username VARCHAR(20) UNIQUE,
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS postgres.superduperdrive.NOTES (
    noteid SERIAL PRIMARY KEY,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS postgres.superduperdrive.FILES (
    fileId SERIAL PRIMARY KEY,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    filedata bytea,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS postgres.superduperdrive.CREDENTIALS (
    credentialid SERIAL PRIMARY KEY,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);
    