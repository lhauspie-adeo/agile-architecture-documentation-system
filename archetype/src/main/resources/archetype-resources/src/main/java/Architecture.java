package ${package};

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;

import org.ndx.agile.architecture.base.ArchitectureModelProvider;

import com.structurizr.Workspace;
import com.structurizr.model.Container;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.ContainerView;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

@ApplicationScoped
public class Architecture implements ArchitectureModelProvider {

	/**
	 * Creates the workspace object and add in it both the architecture components
	 * AND the views used to display it
	 * 
	 * @return
	 */
	public Workspace describeArchitecture() {
		Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
		Model model = workspace.getModel();

		Person user = model.addPerson("User", "A user of my software system.");
		SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System", "My software system.");
		user.uses(softwareSystem, "Uses");

		Container aContainer = softwareSystem.addContainer("TODO", "An example container", "What technology do you use?");
		user.uses(aContainer, "Do something");
		/////////////////////////////////////////////////////////////////////////////////////////
		ViewSet views = workspace.getViews();
		SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext",
				"An example of a System Context diagram.");
		contextView.addAllSoftwareSystems();
		contextView.addAllPeople();

		ContainerView softwareSystemContainers = views.createContainerView(softwareSystem, "software.system.containers", "Software system containers");
		softwareSystemContainers.addAllContainersAndInfluencers();

//		Styles styles = views.getConfiguration().getStyles();
//		styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
//		styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);
		return workspace;
	}

}
