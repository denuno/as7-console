/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.jboss.as.console.client.widgets.tools;

import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * @author Heiko Braun
 * @date 2/28/11
 */
public class ToolStrip extends HTMLPanel {

    private String ref = createUniqueId();


    public ToolStrip() {
        super("");
        getElement().setInnerHTML("<div id='"+ref+"' class='default-toolstrip'>");
    }

    public void addToolButton(ToolButton button)
    {
        add(button, ref);
    }

    public void addToolButtonRight(ToolButton button)
    {
        button.getElement().setAttribute("style", "float:right;border-color:#cccccc;margin-right:5px;");
        add(button, ref);

    }
}
