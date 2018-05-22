# SpiderView
[![Release](https://jitpack.io/v/zhuazhu/SpiderView.svg)](https://jitpack.io/#zhuazhu/SpiderView)

![图片](/images/img.png)

## Gradle
在Project的build.gradle中添加:
   ```
   allprojects {
    	repositories {
    		maven { url 'https://jitpack.io' }
    	}
    }
   ```
添加依赖:
```
implementation 'com.github.zhuazhu:SpiderView:1.0.1'
```

## 布局中的使用
```
<zhuazhu.spider.view.SpiderView
        android:id="@+id/spider"
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:line_count="4"
        app:net_count="6"
        app:line_color="#6b6464"
        app:net_color="#6b6464"
        app:point_color="#f10707"
        app:area_color="#6435f10b"
        app:line_width="0.5dp"
        app:net_width="0.5dp"
        app:point_size="2dp"
        app:value="0.3,0.2,0.6,0.5,0.5,1,1,1"
        />
```
## 属性说明
|属性|默认|说明|
|:---|:---:|:---|
|line_count|3|线的条数|
|net_count|4|网的张数|
|line_color|Color.RED|线的颜色|
|net_color|Color.BLUE|网的颜色|
|point_color|Color.GREEN|点的颜色|
|area_color|#57565865|覆盖区域的颜色|
|line_width|5px|线的宽度|
|net_width|5px|网的宽度|
|point_size|10px|点的大小|
|value|无|维度的值(数字在0到1之间),值的个数为line_count*2,以","号隔开|

## 方法说明
1.setValue(double ...values)

设置维度的值