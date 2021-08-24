package com.yhzc.schooldormitorymobile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextClock;

/**
 * @描述:
 * @创建日期: 2021/4/15 14:00
 * @author: ProcyonLotor
 */
public class MyTextColok extends TextClock {
    /**
     * Creates a new clock using the default patterns for the current locale.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public MyTextColok(Context context) {
        super(context);
    }

    /**
     * Creates a new clock inflated from XML. This object's properties are
     * intialized from the attributes specified in XML.
     * <p>
     * This constructor uses a default style of 0, so the only attribute values
     * applied are those in the Context's Theme and the given AttributeSet.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view
     */
    public MyTextColok(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Creates a new clock inflated from XML. This object's properties are
     * intialized from the attributes specified in XML.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     */
    public MyTextColok(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTextColok(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



}
