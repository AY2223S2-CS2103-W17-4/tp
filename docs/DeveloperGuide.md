---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2223S2-CS2103-W17-4/tp/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/AY2223S2-CS2103-W17-4/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2223S2-CS2103-W17-4/tp/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user attempts to delete a customer/service/appointment/part/technician who has the id of 1  (i.e. `deletecustomer 1`).

This diagram is applicable also for other commands such as the add equivalent.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Omitted from this image is that the `XCommand`, being an  instance of`RedoableCommand`, methods related to it is handled here. For more information, scroll down to the relevant section below.

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2223S2-CS2103-W17-4/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `XListPanel` (`X` is a placeholder for a specific model list panel  e.g., `CustomerListPanel`, `VehicleListPanel`), `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2223S2-CS2103-W17-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2223S2-CS2103-W17-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays specific objects residing in the `Model` such as the `Customer`, `Vehicle`, `Service`, `Appointment` and `PartMap` objects.
* also depends on some mapping classes in the `Model` component as certain objects have an integer array of object ids that refer to another object. One example is the Customer class, which has a `HashSet<Integer>` of `Vehicle` IDs. To associate each `Customer` object with the corresponding `Vehicle` objects, the Model uses an object called `CustomerVehicleMap`. The `UI` component then displays the relevant objects based on these mappings. In essence, the `Model` uses these mapping classes to ensure that objects with references to one another are properly connected and displayed in the user interface.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2223S2-CS2103-W17-4/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCustomerCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a customer).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("deletecustomer 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)


<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

This diagram is applicable to all `deleteXCommand` commands.

Omitted from this image is that the `XCommand`, being an  instance of `RedoableCommand`, methods related to it is handled here. For more information, scroll down to the relevant section below.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XCommandParser` (`X` is a placeholder for the specific command name e.g., `AddCustomerCommandParser`) which uses the other classes shown above to parse the user command and create a `XCommand` object (e.g., `AddCustomerCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XCommandParser` classes (e.g., `AddCustomerCommandParser`, `DeleteCustomerCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2223S2-CS2103-W17-4/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `X` objects (which are contained in a `UniqueXList` object) where X are Customer/Service/Appointments/Vehicles/Technicians, and all `Part` objects (which are contained in a `partMap` object).
* in the case of X, stores the currently 'selected' `X` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<X>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* in the case of Part, stores the currently 'selected' `Part` objects (e.g., results of a search query) as a separate hashmap.
* in the case of ServiceList, it acts functionally the same as an `UniqueXList` except that the entry can be non-unique.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/AY2223S2-CS2103-W17-4/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="1000" />

The `Storage` component,
* can save both AutoM8 shop data and user preference data in json format, and read them back into corresponding objects.
* inherits from both `ShopStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add Feature

### Current Implementation
The add function is facilitated by `AddXCommand` (`X` is a placeholder for the specific entity to be added e.g. `AddCustomerCommand`)

Here `X` can be `Customer/Appointment/Service/Vehicle/Part/Technician`.

The Sequence Diagram below illustrates the interactions within the Logic component for the `execute("addX args*")` API call.

<img src="images/AddXSequenceDiagram.png"/>

The `addX(x)` method of `Model` adds the entity into the system via adding the entity into `Shop`.

### View Feature

### Current Implementation

The view function is facilitated by `ViewXCommand` (`X` is a placeholder for the specific command name e.g., `ViewCustomerCommand`).

Here X can be appointment/customer/service/technician/vehicle.

The Sequence Diagram below illustrates the interactions within the Logic component for the execute("viewcustomer 1") API call.

<img src="images/ViewSequenceDiagram.png"/>

This feature is implemented this way to best match how existing list and find function is done.

This is done this way so that the code is easily maintainable and readable if once the developer understands how `listX` functions works.

However, as you may have noticed, `ViewPartCommand`'s diagram differs slightly.

The Sequence Diagram that illustrates the interactions within the Logic component for the execute("viewpartcommand 1") API call is to be added.

* To add image *






### TotalAppointmentCommand Feature

#### Current Implementation

The `totalAppointmentCommand` feature mainly involves iterating through the appointment list and checking if the specified date falls on the same date as the appointment. The way that the validation check is done is by setting the previous day to be the start date and the next day to be the end date. Finally, we check if the current appointment is within the start and end date.



### Undo/Redo Feature

#### Current Implementation

The undo/redo mechanism is facilitated by `StackUndoRedo`. The implemented undo/redo feature would be best described as two stacks of commands that the user has performed:

- `undoStack` serves to store a "history" of the commands they have performed.
- `redoStack` is a collection of their commands that lead up to initial state at which they started performing the undo.

The central concept is to store a stack of commands that essentially functions as a history-list of the commands. Essentially, we leverage on the stack's data structure which is a linear data structure based on the principle of Last In First Out (LIFO). Based on the implementation described above, `undoStack` is populated by pushing a user's command in the application.

Then, when the user performs an undo, the command is firstly popped from `undoStack` and used to restore previous state, and then we store that command onto `redoStack`.

`StackUndoRedo` contains 2 stacks, `undoStack` and `redoStack`. The `undoStack` and `redoStack` contain commands that are of type `RedoableCommand`. `RedoableCommand` extends Command and has the following attributes and methods.

![UndoRedo0](images/UndoRedo0.png)

When a `RedoableCommand` is being executed, the methods `saveAddressBookSnapshot(Model model)` will be called. This ensures that the current states are stored within the command.

After a command is executed, it will be added into the `StackUndoRedo`. The specific process is explained in the activity diagram below.

![UndoRedo0](images/UndoRedo1.png)

Next, when undo is being performed, `undoStack` will remove the first command in its stack and add it to `redoStack`. It will then call `RedoableCommand` `undo()` of the command that is removed. The `undo()` method will then set the model to the previous snapshot of `saveAddressBookSnapshot`.

Likewise, when redo is being performed, `redoStack` will remove the first command in its stack and add it to `undoStack`. It will then call `RedoableCommand` `redo()` of the command that is removed. The `redo()` method will then execute the command again.

Given below is an example of a usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application. The `StackUndoRedo` will be initialized.

![UndoRedo0](images/UndoRedo2.png)

Step 2. The user executes delete command. The delete command will be pushed into the `undoStack` of `StackUndoRedo`.

![UndoRedo0](images/UndoRedo3.png)

Step 3. The user executes add customer command to add a new customer. Similarly, the add command will be pushed into the `undoStack` of `StackUndoRedo`.

![UndoRedo0](images/UndoRedo4.png)

Step 4. The user now decides that adding of customer was a mistake, and decides to undo that action by executing the undo command.

![UndoRedo0](images/UndoRedo5.png)


> <b>Note:</b> undoCommand will check if there is any command that can be undone by calling `StackUndoRedo` canUndo() method.

The following sequence diagram shows how the undo operation works:

![UndoRedo0](images/UndoRedo6.png)

> <b>Note:</b> The redo command will call `popRedo()` method in `StackUndoRedo`and `redo()` method in `RedoableCommand` .

Step 5. The user executes listcustomers. Due to not being an `UndoCommand` or `RedoCommand`, it causes the `redoStack`
to be cleared.  Commands that are not undoable are not added into the `undoStack`.

![UndoRedo0](images/UndoRedo7.png)



#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Implementation is easy.
    * Cons: Memory usage may cause performance issues.

* **Alternative 2:** Individual command has attached logic that allows it to undo/redo by itself.
    * Pros: Will use less memory (e.g. just save what is being deleted).
    * Cons: Must ensure that the implementation of each command is correct. Adds a lot of complexity that may not seem justified as it is to only accommodate the undo/redo feature.

**Aspect: Data structure to support the undo/redo commands:**

* **Alternative 1 (current choice):** Use 2 stacks to store the history of the Models.
    * Pros: Implementation is easier and the logic would be much more manageable to debug.
    * Cons: Duplicated Logic.

* **Alternative 2:** Use `HistoryManager` for undo/redo.
    * Pros: Does not need to maintain separate stacks and able to use what is in the codebase.
    * Cons: Single Responsibility Principle and Separation of Concerns are violated as `HistoryManager` would need to handle more than one thing. For example, it would need to handle the undo and redo as well as the history of the application. This is in contrast with a HistoryManager which is only responsible for the history of the application.

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Auto repair shop owners who want to keep track of their customers, vehicles, logistics and appointments
* prefers desktop apps over other types
* fast typist
* prefers typing to mouse interactions
* is comfortable interacting with CLI apps

**Value proposition**: AutoM8 provides a platform that allows auto repair shop owners to manage their customer information, service details and logistics



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                  | So that I can…​                                                  |
| -------- |--------------------------------------------|-----------------------------------------------|------------------------------------------------------------------|
| `* * *`  | Auto repair shop owner                     | add appointments I'm attending                | Keep track of appointments for the day                           |
| `* * *`  | Auto repair shop owner                     | add vehicle I want to fix                     | Keep track of vehicle                                            |
| `* * *`  | Auto repair shop owner                     | add spare parts                               | Keep track of how many spare parts remaining                     |
| `* * *`  | Auto repair shop owner                     | add customer                                  | Keep track of customer details                                   |
| `* * *`  | Auto repair shop owner                     | map which car plate belongs to which customer | hand the right car to the appropriate owner                      |
| `* * *`  | Auto repair shop owner                     | delete a contact                              | remove entries I no longer need                                  |
| `* * *`  | Auto repair shop owner                     | find a contact                                | locate details of that contact without searching the entire list |
| `* * *`  | Auto repair shop owner                     | sort vehicles by brand                        | divide the vehicles                                              |
| `* * *`  | Auto repair shop owner                     | edit a contact                                | make changes in case of mistakes                                 |

*{More to be added}*

### Use cases

AutoM8 provides the necessary features that support the management of customer, vehicle, servicing, appointment information such as adding, deleting, listing, sorting, finding and editing. The Use Cases listed below demonstrate their usages.

(For all use cases below, the **System** is `AutoM8` and the **Actor** is the `user`, unless specified otherwise)
<br/><br/>

**Use case: UC01 - Listing all customers**

**MSS**
1. User requests to list out all customers.
2. AutoM8 shows a list of all customers.

   Use case ends.

**Extensions**

- 2a. The list is empty.
  Use case ends.
  <br/><br/>

**Use case: UC02 - Listing all vehicles**

**MSS**
1. User requests to list out all vehicles.
2. AutoM8 shows a list of all vehicles.

   Use case ends.

**Extensions**

- 2a. The list is empty.
  Use case ends.
  <br/><br/>

**Use case: UC03 - Listing all customer appointments**

**MSS**
1. User requests to list out all customer appointments.
2. AutoM8 shows a list of all customer appointments.

   Use case ends.

**Extensions**

- 2a. The list is empty.
  Use case ends.
  <br/><br/>

**Use case: UC04 - Listing all spare parts**

**MSS**
1. User requests to list out all spare parts.
2. AutoM8 shows a list of all spare parts.

   Use case ends.

**Extensions**

- 2a. The list is empty.
  Use case ends.
  <br/><br/>

**Use case: UC05 - Listing all service**

**MSS**
1. User requests to list out all service .
2. AutoM8 shows a list of all service.

   Use case ends.

**Extensions**

- 2a. The list is empty.
  Use case ends.
  <br/><br/>

**Use case: UC06 - Adding a customer**

**MSS**

1. User requests to add a customer as a contact.
2. User inputs the information of the customer.
3. AutoM8 adds the customer as a contact.

   Use case ends.

**Extensions**

- 3a. The given name already exists in AutoM8.
    - 3a1. AutoM8 shows an error message.
      Use case resumes at step 2.
      <br/><br/>

**Use case: UC07 - Adding a vehicle**

**MSS**

1. User requests to add a vehicle into the list.
2. User inputs the information of the vehicle.
3. AutoM8 adds the vehicle as a contact.

   Use case ends.

**Extensions**

- 3a. The given plate number already exists in AutoM8.
    - 3a1. AutoM8 shows an error message.
      Use case resumes at step 2.
      <br/><br/>

**Use case: UC08 - Adding a customer appointment**

**MSS**

1. User requests to add a customer appointment into a list.
2. User inputs the information of the customer appointment.
3. AutoM8 adds the customer appointment into the list.

   Use case ends.

**Extensions**

- 3a. The given customer already has an appointment in AutoM8.
    - 3a1. AutoM8 shows an error message.
      Use case resumes at step 2.
      <br/><br/>

**Use case: UC09 - Adding a service to a vehicle**

**MSS**

1. User requests to add a service into a list.
2. User inputs the information of the service.
3. AutoM8 adds the service into the book.

   Use case ends.

**Extensions**

- 3a. The given vehicle plate number already has a service assigned in AutoM8.
    - 3a1. AutoM8 shows an error message.
      Use case resumes at step 2.
      <br/><br/>

**Use case: UC10 - Adding a spare part**

**MSS**

1. User requests to add a spare parts into a list.
2. User inputs the information of the spare part.
3. AutoM8 adds the spare part into the list.

   Use case ends.

**Extensions**

- 3a. The specified spare part name already exist in the list.
    - 3a1. AutoM8 shows an error message.
      Use case resumes at step 2.
      <br/><br/>


**Use case: UC11 - Editing a customer's details**

**MSS**

1. User requests to <u>list contacts (UC01)</u>.
2. AutoM8 shows a list of customers.
3. User requests to edit a contact on the list.
4. User inputs the updated information.
5. AutoM8 updates the contact's details.

   Use case ends.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>

**Use case: UC12 - Editing a vehicle's details**

**MSS**

1. User requests to <u>list vehicle contacts (UC02)</u>.
2. AutoM8 shows a list of vehicles.
3. User requests to edit a vehicle on the list.
4. User inputs the updated information.
5. AutoM8 updates the vehicle's details.

   Use case ends.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>

**Use case: UC13 - Editing spare part's details**

**MSS**

1. User requests to <u>list spare parts as contacts (UC04)</u>.
2. AutoM8 shows a list of spare parts.
3. User requests to edit a spare part on the list.
4. User inputs the updated information.
5. AutoM8 updates the spare part details.

   Use case ends.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>

**Use case: UC14 - Editing an appointment detail**

**MSS**

1. User requests to <u>list appointment as contacts (UC03)</u>.
2. AutoM8 shows a list of appointments.
3. User requests to edit an appointment on the list.
4. User inputs the updated information.
5. AutoM8 updates the appointment details.

   Use case end.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>

**Use case: UC15 - Editing a service detail**

**MSS**

1. User requests to <u>list of service as contacts (UC05)</u>.
2. AutoM8 shows a list of service.
3. User requests to edit a service on the list.
4. User inputs the updated information.
5. AutoM8 updates the the appointment details.

   Use case end.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>

**Use case: UC16 - Deleting a customer**

**MSS**
1. User requests to <u>list customers (UC01)</u>.
2. AutoM8 shows a list of customers.
3. User requests to delete a customer at a given index.
4. AutoM8 deletes the customer at the index.

   Use case ends.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>

**Use case: UC17 - Deleting a vehicle**

**MSS**
1. User requests to <u>list of vehicles (UC02)</u>.
2. AutoM8 shows a list of vehicle.
3. User requests to delete a vehicle at a given index.
4. AutoM8 deletes the contact at the index.

   Use case ends.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>

**Use case: UC18 - Deleting a customer appointment**

**MSS**
1. User requests to <u>list of appointments (UC03)</u>.
2. AutoM8 shows a list of appointments.
3. User requests to delete an appointment at a given index.
4. AutoM8 deletes the appointment at the index.

   Use case ends.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>

**Use case: UC19 - Deleting a spare part**

**MSS**
1. User requests to <u>list of spare parts (UC04)</u>.
2. AutoM8 shows a list of spare parts.
3. User requests to delete a spare part at a given index.
4. AutoM8 deletes the contact at the index.

   Use case ends.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>


**Use case: UC20 - Sorting vehicles**

**MSS**
1. User requests to <u>list vehicles (UC01)</u>.
2. AutoM8 shows a list of vehicles.
3. User requests to sort vehicles in list.
4. AutoM8 sorts vehicles according to user's requirements.

   Use case ends.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. No fields are specified.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>

**Use case: UC21 - Find a customer**

**MSS**

1. User requests to <u>list of customer as contacts (UC01)</u>.
2. AutoM8 shows a list of customer.
3. User requests to find customer on the list.
4. AutoM8 find customer according to user's requirements.

   Use case end.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>


**Use case: UC22 - Find a vehicle**

**MSS**

1. User requests to <u>list of vehicle as contacts (UC02)</u>.
2. AutoM8 shows a list of vehicle.
3. User requests to find vehicle on the list.
4. AutoM8 find vehicle according to user's requirements.

   Use case end.

**Extensions**
- 2a. The list is empty.
  Use case ends.
- 3a. The provided index is invalid.
    - 3a1. AutoM8 displays an error message.

      Use case resumes at step 2.
      <br/><br/>


**Use case: UC23 - Exiting the application**

**MSS**

1. User requests to exit AutoM8.
2. AutoM8 closes.

   Use case ends.
   <br/><br/>

### Non-Functional Requirements

1. The application should be _free_.
2. It should be easy to understand and use, even for users with little to no experience.
3. Offline application used by each person.
4. The application should be able to operate on any _mainstream OS_ such as Linux, MacOS and Windows so long as Java 11 or above is installed.
5. The product should be highly testable.
6. Use of clear and concise English should be observed in the documentation
7. This product does not necessarily need to be installed but can run as an executable.
8. Contain clear and easy to understand error messages
9. Should be able to support up to 1000 persons without any noticeable lag in performance for typical usage.
10. A user that possess above average typing speed for regular text (i.e. not code, not system admin commands) should be able to achieve majority of the task faster using commands than using the mouse.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Vehicle**: A 4-wheel machine used to transport people
* **Plate number**: An identifier put on the front and back of a vehicle
* **Spare parts**: A duplicate part of a vehicle that can be used to replace a broken part in a car

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
