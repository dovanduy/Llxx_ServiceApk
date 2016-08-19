/**
 * 
 */
package com.llxx.socket.action;

import java.util.ArrayList;

/**
 * @author 李万隆
 * @date   2016年8月19日
 * @qq 	461051353
 * @company 北京微云车联科技有限公司
 * @describe 类描述
 */
public class AccessibilityActionConfig
{
    public static final ArrayList<AccessibilityAction> ACTIONS = new ArrayList<AccessibilityAction>();

    static
    {
        ACTIONS.add(new AccessibilityClickAction());
        ACTIONS.add(new AccessibilityToastAction());
    }
}
