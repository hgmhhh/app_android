// Generated code from Butter Knife. Do not modify!
package com.wemolian.app.wml;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FragmentFunc$$ViewBinder<T extends com.wemolian.app.wml.FragmentFunc> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361933, "field 'mSlidingPlayView'");
    target.mSlidingPlayView = finder.castView(view, 2131361933, "field 'mSlidingPlayView'");
    view = finder.findRequiredView(source, 2131361799, "field 'gridview' and method 'select'");
    target.gridview = finder.castView(view, 2131361799, "field 'gridview'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.select(p2);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mSlidingPlayView = null;
    target.gridview = null;
  }
}
