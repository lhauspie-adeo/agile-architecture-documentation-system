<h1 align="center">Welcome to Agile architecture documentation archetype 👋</h1>
<p>
	<a href="https://github.com/Riduidel/agile-architecture-documentation-system/actions?query=workflow%3A%22Java+CI+with+Maven%22">
<img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Riduidel/agile-architecture-documentation-system/Java CI with Maven">
	</a>
  <a href="https://github.com/Riduidel/agile-architecture-documentation-system/releases" target="_blank"><img src="https://badge.fury.io/gh/Riduidel%2Fagile-architecture-documentation-system.svg" alt="GitHub version"></a>
  <a href="https://twitter.com/Riduidel" target="_blank">
    <img alt="Twitter: Riduidel" src="https://img.shields.io/twitter/follow/Riduidel.svg?style=social" />
  </a>
</p>

> A Maven archetype allowing you to easily create your agile architecture documentation using a mix of C4, Asciidoc and PlantUML. This archetype uses [Structurizr](https://github.com/structurizr/java/) to build the architecture model, and [Agile architecture documentation](http://www.codingthearchitecture.com/2016/05/31/agile_software_architecture_documentation.html) template, all by Simon Brown.

## Install

Since I use Jitpack for easy deployment of that archetype, and [maven requires archetypes to be defined in settings.xml](http://maven.apache.org/archetype/maven-archetype-plugin/archetype-repository.html), you first have to define a jitpack profile in your settings.xml this way

```xml
		<profile>
			<id>jitpack</id>
			<repositories>
				<repository>
					<id>archetype</id><!-- id expected by maven-archetype-plugin to avoid 
						fetching from everywhere -->
					<url>https://jitpack.io</url>
					<releases>
						<enabled>true</enabled>
						<checksumPolicy>fail</checksumPolicy>
					</releases>
					<snapshots>
						<enabled>true</enabled>
						<checksumPolicy>warn</checksumPolicy>
					</snapshots>
				</repository>
			</repositories>
		</profile>
```

Then, you can use the archetype by running this maven-friendly 😅command.
Don't forget to replace `${VERSION}` by ![GitHub version](https://badge.fury.io/gh/Riduidel%2Fagile-architecture-documentation-system.svg)

```sh
mvn archetype:generate -DarchetypeGroupId=io.github.Riduidel.agile-architecture-documentation-system -DarchetypeArtifactId=archetype
```

This will ask you a few questions and generate the project.
Finally, don't forget to replace the value of `agile-architecture-version` maven property by ![GitHub version](https://badge.fury.io/gh/Riduidel%2Fagile-architecture-documentation-system.svg)

## Usage

Once the archetype has been run, you'll have a project with Structurizr compatible source in `src/main/java`
and asciidoc files following Agile architecture documentation template in `src/docs/asciidoc`.

### Generating architecture documentation
Running `mvn install` will 

1. compile and run Java code to have C4 model-compatible diagrams generated by PlantUML
1. generate AsciiDoc HTML and PDF files

### Using the archetype at 100%
The archetype contains two Java classes.

1. `Architecture` contains initial definition of your architecture. 
This is where you should put all the systems, containers, components that are directly described.
2. `ViewsGenerator` allow you to generate the various views by using elements of the architecture model.

### Faster edit loop
A faster developer feedback loop can be achieved using the [fizzed-watcher-maven-plugin](https://github.com/fizzed/maven-plugins).
Don't worry, you don't have to configure it by yourself!
You can run

* `mvn -Pgenerate-docs -Plivereload` when working on documents.
This will watch both the `src/docs` and the `src/main/java` folder and run a `mvn package` when any of these folders have changes in.
Visit [http://localhost:35729/docs/html/](http://localhost:35729/docs/html/) to view your generated document in HTML form
* `mvn -Pgenerate-docs -Plivereload` when working on slides.
This will watch both the `src/slides` and the `src/main/java` folder and run a `mvn package` when any of these folders have changes in.
Visit [http://localhost:35729/slides/html/](http://localhost:35729/slides/html/) to view your generated slides in HTML form

If you have installed the [livereload browser extension](http://livereload.com/extensions/) (but not the livereload desktop application, which job is handled by the maven build), any change in the project will be immedialty visible in browser, allowing you to work in a pleasant environment (well, I hope)

### Best practices
* Define systems, containers and components options **only** through structurizr properties. 
The useful method for that is `ModelItem#addProperty(String, String)`. Don't try to load properties from other means, cause it'll introduce incoherence.
* Try to stay close to describe=>extend=>generate. In other words, first describe architecture in `Architecture` class. 
Then use available extension points (provided by CDI) to add additionnal infos.

#### describe=>extend=>generate
What are we talking about here ?
In fact, the simplest way to have a good model, from what w've already tested, is to

1. Create a valid and complete model, by either describing all elements or finding them (using enhancers like `MavenDetailsInfererEnhancer`)
2. Extend that model by adding associated resources (that's typically the case of the `SCMLinkGenerator` and `SCMReadmeReader`)
3. Generate the good resources, like the viewss (using the archetype proveided `ViewsGenerator`) and the document includes

#### Writing an Enhancer
Since we're talking about the `Enhancer` interface, this is the main interface allowing us to have an extendable architecture model.
So how to write an `Enhancer` ? 
First, choose what to enhance : model or views ? 
Both of them have dedicated subinterfaces (`ModelEnhancer` and `ViewEnhancer`).
There even is a `ModelElementAdapter` that will ease things out for model enhancers, since it's the interface you may extend.
So, once you've chosen what to extend, choose when this enhancer will run by setting a priority.
This priority defines the order in whcih the enhacer will run, and all running enhancers are displayed ordered by priority at start of generation.
Now, you'll have to implement the visiting methods, for which you can find numerous examples in our code.
Don't forget to take a look at the `isParallel()` method, which may fasten things a lot, since it can allow the enhancer to be run using parallel features of Java system executor services.

## Developing
There are not many things to do (except improving the archetype source).
However, if you want to improve things, 
please run `mvn verify` which will create a project from the archetype and 
run `mvn package` which will trigger Java class compilation and run and Asciidoc documentation generation.

### Releasing
Can be performed only on a machine having Nicolas Delsaux GPG key allowing to sign to maven central (not yet enabled on GitHub).

Don't forget to activate the `-Prelease` profile, which enable all the good things (Sonatype staging, signing, ...)

## Architecture
Way more details are available in the [architecture documentation (which uses this system, obviously)](https://riduidel.github.io/agile-architecture-documentation-system/).

## Author

👤 **Nicolas Delsaux**

* Twitter: [@Riduidel](https://twitter.com/Riduidel)
* Github: [@Riduidel](https://github.com/Riduidel)

## 🤝 Contributing

Contributions, issues and feature requests are welcome!<br />Feel free to check [issues page](https://github.com/Riduidel/agile-architecture-documentation-system/issues).

## Show your support

Give a ⭐️ if this project helped you!

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_
