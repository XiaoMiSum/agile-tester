一个敏捷的测试平台(开发中)

1. git clone git@github.com:XiaoMiSum/springboot-migoo-framework.git
2. cd springboot-migoo-framework
3. mvn clean install
4. git clone git@github.com:XiaoMiSum/agile-tester.git
5. exec sql/agile-tester.sql
6. modify application-dev.yaml datasource to yourself
7. exec redis-server
8. run xyz.migoo.agiletest.AgineTester
9. access http://127.0.0.1:48080/