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

package org.jboss.as.console.client.domain.groups.deployment;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.as.console.client.domain.model.ServerGroupRecord;
import org.jboss.as.console.client.widgets.ContentGroupLabel;
import org.jboss.as.console.client.widgets.DefaultButton;
import org.jboss.as.console.client.widgets.forms.ComboBoxItem;
import org.jboss.as.console.client.widgets.forms.Form;
import org.jboss.as.console.client.widgets.forms.FormValidation;
import org.jboss.as.console.client.widgets.forms.TextBoxItem;
import org.jboss.as.console.client.widgets.forms.TextItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Braun
 * @date 4/8/11
 */
public class DeploymentStep2 {

    private NewDeploymentWizard wizard;

    private Form<DeploymentReference> form;

    public DeploymentStep2(NewDeploymentWizard wizard) {
        this.wizard = wizard;
    }

    public Widget asWidget()
    {
        VerticalPanel layout = new VerticalPanel();
        layout.getElement().setAttribute("style", "width:95%, margin:15px;");

        layout.add(new HTML("<h3>Step 2/2: Chose Server Group</h3>"));

        List<String> groupNames = new ArrayList<String>();
        for(ServerGroupRecord sg : wizard.getPresenter().getServerGroups())
            groupNames.add(sg.getGroupName());

        form = new Form<DeploymentReference>(DeploymentReference.class);

        TextItem hashField = new TextItem("hash", "Key");
        TextBoxItem nameField = new TextBoxItem("name", "Name");
        ComboBoxItem groupSelector = new ComboBoxItem("group", "Server Group");
        groupSelector.setDefaultToFirstOption(true);
        groupSelector.setValueMap(groupNames);

        form.setFields(hashField, nameField, groupSelector);

        layout.add(form.asWidget());

        // -----

        Label cancel = new Label("Cancel");
        cancel.setStyleName("html-link");
        cancel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                wizard.getPresenter().closeDialoge();
            }
        });

        DefaultButton submit = new DefaultButton("Finish", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                FormValidation validation = form.validate();
                if (!validation.hasErrors()) {
                    // proceed
                    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand()
                    {
                        @Override
                        public void execute() {
                            wizard.getPresenter().onDeployToGroup(form.getUpdatedEntity());
                        }
                    });

                }
            }
        });

        HorizontalPanel options = new HorizontalPanel();
        options.getElement().setAttribute("style", "margin-top:10px;width:100%");

        HTML spacer = new HTML("&nbsp;");
        options.add(spacer);

        options.add(submit);
        options.add(spacer);
        options.add(cancel);
        cancel.getElement().getParentElement().setAttribute("style", "vertical-align:middle");
        submit.getElement().getParentElement().setAttribute("align", "right");
        submit.getElement().getParentElement().setAttribute("width", "100%");

        layout.add(options);

        return layout;
    }


    void edit(DeploymentReference ref)
    {
        form.edit(ref);
    }
}
