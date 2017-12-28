# study_code

## How to

**Step 1.** Add the JitPack repository to your build file

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
**Step 2.** Add the dependency

```groovy
dependencies {
	compile 'com.github.songsongtao:fast_code:v1.2'
}
```
## Features

### v1.2

- 添加压缩工具`Compressor`

### v1.1

- 丰富工具类
- 封装提示框，`popWindow`
- 点赞view,空布局，正方形布局，`banner`,带删除的`EditText`等

### v1.0

- 基本的工具类
- `android6.0`权限封装
- `android7.0`文件共享封装
- 自定义`TextView/RelativeLayout,`支持边框,圆角,按压效果等
- 图片压缩封装,以及简单异步批量处理图片

## History
- `MPermissionUtils`(内存泄漏)改为`SPermissionUtils`