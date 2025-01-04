# 題目
SpringEL demo

## 環境
Java11+ Spring Boot + Spring Data JPA + H2 +JUnit4

## 結果展示

通過
```bash
http://localhost:8081/test/param?rule=124&age=19&nationality=TW
```

![pass.jpg](./pass.jpg)


不通過
```bash
http://localhost:8081/test/param?rule=124&age=17&nationality=CH
```
![fail.jpg](./fail.jpg)
 


                                                


