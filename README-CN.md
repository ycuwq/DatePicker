# DatePicker

Android 日期选择器控件。

![screen](https://raw.githubusercontent.com/ycuwq/DatePicker/master/screenshots/device-2018-01-11-193707.gif)

[Repo Wiki](https://github.com/ycuwq/DatePicker/wiki)

## 引入

在 app modules加入依赖:

```
dependencies {
	implementation 'com.ycuwq.widgets:datepicker:1.0.1'
}
```

### 在XML中配置

```
<com.ycuwq.datepicker.date.DatePicker
    android:id="@+id/datePicker"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

### 显示弹窗

![screen](https://raw.githubusercontent.com/ycuwq/DatePicker/master/screenshots/device-2018-01-11-201208.gif)

弹窗继承了 `DialogFragment` ，使用方式：

```
DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
datePickerDialogFragment.setOnDateChooseListener(...);
datePickerDialogFragment.show(getFragmentManager(), "DatePickerDialogFragment");
```

如果想自定义属性需要继承`DialogFragment` ，例如：

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

## 协议

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

