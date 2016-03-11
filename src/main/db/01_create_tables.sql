use bd;
#use bd_test;

CREATE TABLE t (
	EMAIL varchar(254) NOT NULL,
  	NOTIFICATION_DATE datetime NOT NULL,
  	STATUS_OF_NEXT varchar(11) NOT NULL DEFAULT 'NONE' COMMENT 'Possible values: NONE, IN_PROGRESS, SENT',
  	PRIMARY KEY (EMAIL, NOTIFICATION_DATE)
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;

