![DatePicker](https://github.com/Tobaloidee/DatePicker/blob/master/logo/logotype.png)

[![](https://jitpack.io/v/ycuwq/DatePicker.svg)](https://jitpack.io/#ycuwq/DatePicker)  [![MIT](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://opensource.org/licenses/mit-license.php)

Android date picker widget.

![screen](https://raw.githubusercontent.com/ycuwq/DatePicker/master/screenshots/device-2018-01-11-193707.gif)

[Repo Wiki](https://github.com/ycuwq/DatePicker/wiki)

[简体中文说明](./README-CN.md)
[实现思路文档](https://github.com/ycuwq/DatePicker/wiki/%E8%AE%BE%E8%AE%A1%E6%80%9D%E8%B7%AF)
## Import

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency to your app modules:

```
dependencies {
	implementation 'com.github.ycuwq:DatePicker:latest-version'
}
```

## Usage

```
<com.ycuwq.datepicker.date.DatePicker
    android:id="@+id/datePicker"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

### Show Dialog

![screen](https://raw.githubusercontent.com/ycuwq/DatePicker/master/screenshots/device-2018-01-11-201208.gif)

`DatePickerDialogFragment`extends `DialogFragment` ，for example:

```
DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
datePickerDialogFragment.setOnDateChooseListener(...);
datePickerDialogFragment.show(getSupportFragmentManager(), "DatePickerDialogFragment");
```

If you want to customize the style ，for example:：

```
public class MyDatePickerDialogFragment extends DatePickerDialogFragment {

   @Override
   protected void initChild() {
      super.initChild();
      mCancelButton.setTextSize(mCancelButton.getTextSize() + 5);
      mDecideButton.setTextSize(mDecideButton.getTextSize() + 5);
      mDatePicker.setShowCurtain(false);
   }
}
```

## License

```
MIT License

Copyright (c) 2018 ycuwq

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```



