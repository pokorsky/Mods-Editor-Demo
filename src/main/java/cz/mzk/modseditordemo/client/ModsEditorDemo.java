/*
 * Copyright (C) 2012 Jan Pokorsky
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.mzk.modseditordemo.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Entry point classes define
 * <code>onModuleLoad()</code>.
 */
public class ModsEditorDemo implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final ModsPresenter modsTabHolder = new ModsPresenter();
        Canvas buttonLayout = createButtons(modsTabHolder);

        VLayout rootPanel = new VLayout(2);
        rootPanel.setHeight100();
        rootPanel.setWidth100();
        rootPanel.setMembers(buttonLayout, modsTabHolder.getUI());

        rootPanel.draw();
    }

    private Canvas createButtons(final ModsPresenter modsTabHolder) {
        IButton btnNew = new IButton("New");
        btnNew.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                modsTabHolder.newData();
            }
        });
        IButton btnLoad = new IButton("Load");
        btnLoad.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                modsTabHolder.loadData("id:sample");
            }
        });
        IButton btnSave = new IButton("Save");
        btnSave.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                modsTabHolder.saveData();
            }
        });
        HLayout buttonLayout = new HLayout(2);
        buttonLayout.setLayoutMargin(4);
        buttonLayout.setMembers(btnNew, btnLoad, btnSave);
        return buttonLayout;
    }
}
