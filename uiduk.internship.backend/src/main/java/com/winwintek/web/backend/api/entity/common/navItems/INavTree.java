package com.winwintek.web.backend.api.entity.common.navItems;

public interface INavTree {

    Long getId();

    String getName();

    Long getParentId();

    String getScreenName();

    String getRouter();

    String getVnName();

    int getDisplayOrder();

    int getTreeLevel();

    Long getTreeRoot();

    String getBlankScreen();

}
