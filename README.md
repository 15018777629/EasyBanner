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
        easyBanner.setBanner(dotIndicator, new DefaultBannerAdapter(Arrays.asList(IMAGES) , true,this) {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(BannerActivity.this)
                        .load(url)
                        .skipMemoryCache()
                        .into(imageView);
            }
        });
```

