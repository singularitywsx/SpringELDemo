INSERT INTO SCENE VALUES ('1', '10000', 'xxxx',  '#AuthContextFact.rule ==''124''', '是否可用');
INSERT INTO SCENE VALUES ('2', '10001', 'xxxx',  '#AuthContextFact.rule ==''147''', '是否可用');

INSERT INTO RULE VALUES ('1', '10000', 'userExistAndStatusEnable',  '#UserFact.exists ==true and #UserFact.status =="ENABLE" ','AUTH_RULE', '用戶存在和是否可用不過');
INSERT INTO RULE VALUES ('2', '10000', 'ageCheck',  '#UserFact.age >=18','AUTH_RULE', '年齡檢核不過');
INSERT INTO RULE VALUES ('3', '10000', 'NationalityCheck',  '#UserFact.nationality =="TW" ','AUTH_RULE', '國別不是TW');