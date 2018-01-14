# DatePicker

[![Download](https://api.bintray.com/packages/ycuwq/android/datepicker/images/download.svg)](https://bintray.com/ycuwq/android/datepicker/_latestVersion)[![MIT](https://img.shields.io/github/license/mashape/apistatus.svg)]()

Android date picker widget.

![screen](https://raw.githubusercontent.com/ycuwq/DatePicker/master/screenshots/device-2018-01-11-193707.gif)

[Repo Wiki](https://github.com/ycuwq/DatePicker/wiki)

 [简体中文说明](./README-CN.md)

## Import 

Add the dependency to your app modules:

```
dependencies {
	implementation 'com.ycuwq.widgets:datepicker:latest-version'
}
```

## Config in xml:

```
<com.ycuwq.datepicker.date.DatePicker
    android:id="@+id/datePicker"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

## Show Dialog

![screen](https://raw.githubusercontent.com/ycuwq/DatePicker/master/screenshots/device-2018-01-11-201208.gif)

`DatePickerDialogFragment`extends `DialogFragment` ，for example:

```
DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
datePickerDialogFragment.setOnDateChooseListener(...);
datePickerDialogFragment.show(getFragmentManager(), "DatePickerDialogFragment");
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



