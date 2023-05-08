# android_sushe
# 安卓Android学生宿舍报修管理系统

系统开发环境: Windows + Myclipse(服务器端) + Eclipse(手机客户端) + mysql数据库

服务器也可以用Eclipse或者idea等工具，客户端也可以采用android studio工具！

系统客户端和服务器端架构技术: 界面层，业务逻辑层，数据层3层分离技术，MVC设计思想！

服务器和客户端数据通信格式：json格式,采用servlet方式

【服务器端采用SSH框架，请自己启动tomcat服务器，hibernate会自动生成数据库表的哈！】

hibernate生成数据库表后，只需要在admin管理员表中加个测试账号密码就可以登录后台了哈！

下面是数据库的字段说明：

学生信息: 学号,登录密码,所在班级,姓名,性别,出生日期,照片,联系方式,所在房间,附加信息

宿舍楼: 宿舍id,宿舍楼名称

房间信息: 房间id,所在宿舍,房间名称,床位数

报修信息: 报修id,报修类别,故障简述,故障详述,上报学生,处理结果,维修状态

维修状态: 状态id,状态名称

报修类别: 维修类别id,维修类别名称

晚归信息: 晚归记录id,晚归学生,晚归原因,晚归时间

公告信息: 公告id,公告标题,公告内容,公告时间