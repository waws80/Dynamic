    # Dynamic
一个类似于okhttp+retrofit的路由框架

路由基本用法：[http://www.waws.top/content.html?api=L1JvdXRlci5tZA==]('http://www.waws.top/content.html?api=L1JvdXRlci5tZA==')

```java
//service
@Path("third")
 // @Action(MediaStore.ACTION_IMAGE_CAPTURE)
  //@Uri(value = "http://www.waws.top/second?id=1")
  @SkipIntecepter
  @RequestCode(100)
 // @Anims(enter = 1,exit = 1)
  Call toSecond(@QueryBundle Bundle query,
          @BundleOption Bundle option,
          @ResultCall ResultCallBack callBack);
```

```java
//构建Dynamic
Dynamic dynamic = new Dynamic.Builder().router(Router.getInstance()).build();
//构建service
RouterService service = dynamic.create(RouterService.class,this);

Bundle bundle = new Bundle();
bundle.putString("id","100");
bundle.putString("name","thanatos");
//获取call
Call call = service.toSecond(bundle, null, new ResultCallBack() {
    @Override
    public void next(int resultCode, Intent data) {
        RouterLog.d("resultCode:" +resultCode +"  data:"+data.getStringExtra("result"));
    }
});
//获取response
Response response = call.execute();
```
