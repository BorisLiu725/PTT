#   电影API

##  1.添加电影
    url:http://manage.ptt.com/movie/add
    post请求
     {
       "movieName": "何以为家ququququuquququqqu",
       "movieDirect": "娜丁·拉巴基",
       "moviePerformer": "赞恩·阿尔·拉菲亚,约丹诺斯·希费罗",
       "movieType": 4,
       "movieArea": "黎巴嫩,法国,美国",
       "movieTime": 117,
       "movieDesc": "法庭上，十二岁的男孩赞恩向法官状告他的亲生父母，原因是，他们给了他生命。是什么样的经历让一个孩子做出如此不可思议的举动？故事中，赞恩的父母在无力抚养和教育的状况下依然不停生育，作为家中的长子赞恩，弱小的肩膀承担了无数生活的重压。当妹妹被强行卖给商贩为妻时，赞恩愤怒离家，之后遇到一对没有合法身份的母子，相互扶持勉强生活。然而生活并没有眷顾赞恩，重重磨难迫使他做出了令人震惊的举动……",
       "movieImages": "http://ppt/1.jpg",
       "star": 10.0
       "language":"国语",
       "startTime":1557488558000,
     }

## 2. 添加演出安排
       {
         "movieId":1,
         "studioId":10,
         "newPrice":1034,
         "oldPrice":1532,
         "seatNum" : 0,
         "startTime":1557235970030,
         "endTime":1557235971220
       }
       
   
  ##3. 保存订单
     {
       "movieId": 1,
       "scheduleId":2,
       "userId":1,
       "totalPrice":1234,
       "orderDetails":[
         {
           "orderId":"1asd1f231sd",
           "row":1,
           "column":2
         },
         {
           "orderId":"1asd1f231sd",
           "row":1,
           "column":3
         }
       ]
     }
     
 
 
 ##4.添加演出厅
 
    uri:http://localhost:8001/studio/add
   
    {
    	"studioName":"3号厅",
    	"studioType":1,
    	"rowsCount":3,
      	"colsCount":4
    }