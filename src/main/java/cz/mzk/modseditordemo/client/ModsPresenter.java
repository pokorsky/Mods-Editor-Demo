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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.view.ModsTab;
import java.util.Arrays;

/**
 * A simple wrapper for the {@link ModsTab} component.
 *
 * @author Jan Pokorsky
 */
final class ModsPresenter {
    
    private ModsTab modsTab;
    private ModsCollectionClient mods;
    private VLayout modsContainer;
    private String id;

    public ModsPresenter() {
        modsContainer = new VLayout();
        modsContainer.setHeight100();
        modsContainer.setWidth100();
    }

    public void loadData(String id) {
        this.id = id;
        replaceContents("Loading MODS data...");
        ModsServiceAsync service = ModsServiceAsync.Util.getInstance();
        service.read(id, new AsyncCallback<ModsCollectionClient>() {

            public void onFailure(Throwable caught) {
                replaceContents("Loading FAILED!");
                GWT.log("data loading failed", caught);
            }

            public void onSuccess(ModsCollectionClient result) {
                modsTab = new ModsTab(1, result);
                VLayout modsLayout = modsTab.getModsLayout();
                replaceContents(modsLayout);
            }
        });
    }

    public void saveData() {
        replaceContents("Saving MODS data...");
        ModsTypeClient mc = this.modsTab.getMods();
        ModsCollectionClient mcc = new ModsCollectionClient();
        mcc.setMods(Arrays.asList(mc));
        ModsServiceAsync service = ModsServiceAsync.Util.getInstance();
        service.write(this.id, mcc, new AsyncCallback<String>() {

            public void onFailure(Throwable caught) {
                replaceContents("Save FAILED!");
                GWT.log("data saving failed", caught);
            }

            public void onSuccess(String newOrExistingId) {
                ModsPresenter.this.id = newOrExistingId;
                loadData(newOrExistingId);
            }
        });
    }

    public void newData() {
        this.id = null;
        this.modsTab = new ModsTab(1);
        replaceContents(modsTab.getModsLayout());
    }

    public Canvas getUI() {
        if (mods == null) {
            modsTab = new ModsTab(1);
        } else {
            modsTab = new ModsTab(1, mods);
        }
        replaceContents(modsTab.getModsLayout());
        return modsContainer;
    }

    private void replaceContents(String msg) {
        modsContainer.setMembers();
        modsContainer.setContents(msg);
    }

    private void replaceContents(Canvas members) {
        modsContainer.setContents(" ");
        modsContainer.setMembers(members);
    }

}
