# EasyBanner 超简单的轮播图
使用超简单，十行代码就可以搞定   
支持自定义指示器，使用方便   
支持自动轮播   
支持无限轮播   
支持轮播事件监听   
支持自定义轮播图布局

## OK,废话不多说，先看看如何使用
```java
EasyBanner easyBanner = (EasyBanner) findViewById(R.id.easyBanner);
DotIndicator dotIndicator = (DotIndicator) findViewById(R.id.dotIndicator);
// 三个参数分别为：数据集合，是否无限轮播，上下文
easyBanner.setBanner(dotIndicator, new DefaultBannerAdapter(List<T> , true,context) {
      @Override
      public void loadImage(ImageView imageView, String url) {
          // 展示你的图片
      }
 });
```
甚至不用十行代码，就可以展示你的轮播图啦，这是一个最简单的轮播图，只有一张图片和指示器，当然还有xml布局如下   
```java
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    // 轮播图
    <com.yxr.easybanner.widget.EasyBanner
        android:id="@+id/easyBanner"
        android:layout_width="match_parent"
        android:layout_height="175dp"
        app:auto_play="true"
        app:interval="3000"/>

    // 轮播图指示器
    <com.yxr.easybanner.widget.DotIndicator
        android:id="@+id/dotIndicator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="150dp"
        android:layout_centerHorizontal="true"
        app:dot_type="custom_dot"
        app:dot_size="20dp"
        app:dot_space="7dp"
        app:dot_color="#AA000000"
        app:dot_select_color="#fff"
        />
</RelativeLayout>
```
![Screenshot](https://github.com/15018777629/EasyBanner/blob/master/screenshot/Screenshot_20170815-144423.png)
## 稍微解释一下这些属性的使用   
```java
// EasyBanner的属性
app:auto_play="true"                             // 是否自动轮播
app:interval="3000"                              // 自动轮播时间间隔

// DotIndicator的属性
app:dot_type="custom_dot"                        // 指示器类型custom_dot对应的是自定义图片，default_dot对应的是默认的圆点
app:dot_size="20dp"                              // 每个dot的大小
app:dot_space="7dp"                              // 每个dot之间的间隔大小
app:dot_color="#AA000000"                        // dot的颜色  dot_type为default_dot时生效
app:dot_select_color="#fff"                      // 选择（当前）dot的颜色  dot_type为default_dot时生效
app:dot_image="@drawable/line"                   // dot的图片资源  dot_type为custom_dot时生效
app:dot_select_image="@drawable/line_select"     // 选择（当前）dot的图片资源  dot_type为custom_dot时生效
```
当然，有懒人模式只加载图片，当然也有自定义才能满足你们的要求，其实也是很简单，只需要实现展示的逻辑即可，参考如下
```java
easyBanner.setBanner(dotIndicator, new BaseBannerAdapter(Arrays.asList(IMAGES) , true) {
    @Override
    public View instantiateItem(ViewGroup container, Object o) {
        String url = (String) o;
        View v = View.inflate(BannerActivity.this, R.layout.layout_banner, null);
        ImageView ivPic = (ImageView) v.findViewById(R.id.ivPic);
        TextView tvUrl = (TextView) v.findViewById(R.id.tvUrl);

        Picasso.with(BannerActivity.this)
               .load(url)
               .skipMemoryCache()
               .into(ivPic);
        tvUrl.setText(url);
        container.addView(v);
        return v;
    }
});
```
![Screenshot](https://github.com/15018777629/EasyBanner/blob/master/screenshot/Screenshot_20170815-141458.png)
## 没错，就是这么简单，再也不用写冗余的代码了。哦，忘了和你们说了，如果auto_play 属性设为了true，建议进行如下操作
```java
    @Override
    protected void onResume() {
        super.onResume();
        easyBanner.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        easyBanner.onPause();
    }
 ```
