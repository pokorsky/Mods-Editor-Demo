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
package cz.mzk.modseditordemo.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;
import cz.fi.muni.xkremser.editor.server.mods.ModsType;
import cz.fi.muni.xkremser.editor.server.mods.ObjectFactory;
import cz.fi.muni.xkremser.editor.server.mods.TitleInfoType;
import cz.fi.muni.xkremser.editor.server.util.BiblioModsUtils;
import cz.mzk.modseditordemo.client.ModsService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;

/**
 * Simple MODS provider.
 *
 * @author Jan Pokorsky
 */
public final class ModsServiceProvider extends RemoteServiceServlet implements ModsService {

    private static final Logger LOG = Logger.getLogger(ModsServiceProvider.class.getName());

    private static final Map<String, ModsCollection> STORAGE = new HashMap<String, ModsCollection>();

    @Override
    public void init() throws ServletException {
        ObjectFactory objFactory = new ObjectFactory();
        ModsCollection mods = new ModsCollection();
        ModsType modsType = new ModsType();
        TitleInfoType titleInfoType = new TitleInfoType();

        titleInfoType.getTitleOrSubTitleOrPartNumber().add(objFactory.createBaseTitleInfoTypeTitle("Title Sample"));
        modsType.setModsGroup(Arrays.<Object>asList(titleInfoType));
        mods.setMods(Arrays.asList(modsType));

        STORAGE.put("id:sample", mods);
    }


    public ModsCollectionClient read(String id) {
        ModsCollection mods;
        synchronized (STORAGE) {
            mods = STORAGE.get(id);
            if (mods == null) {
                throw new IllegalArgumentException("Invalid id: " + id);
            }
        }
        LOG.log(Level.INFO, "id: {0}MODS: {1}", new Object[]{id, BiblioModsUtils.toXML(mods)});
        return BiblioModsUtils.toModsClient(mods);
    }

    public String write(String id, ModsCollectionClient modsClient) {
        String oldId = id;
        LOG.log(Level.INFO, "id: {0}, modsClient: {1}", new Object[]{id, modsClient});
        ModsCollection mods = BiblioModsUtils.toMods(modsClient);
        String xml = BiblioModsUtils.toXML(mods);
        LOG.log(Level.INFO, "id: {0}, MODS: {1}", new Object[]{id, xml});

        synchronized(STORAGE) {
            if (id == null) {
                id = "id:" + STORAGE.size();
            }
            STORAGE.put(id, mods);
        }

        LOG.log(Level.INFO, "written id: {0}, old id: {1}", new Object[]{id, oldId});

        return id;
    }

}
