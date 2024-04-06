package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Directs users to the HTML website with email links to all the students in the current list.
 */
public class MailCommand extends Command {

    public static final String COMMAND_WORD = "mail";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": generates mailto link to students from "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " LAB10 TUT04";

    public static final String MESSAGE_NO_PERSON = "Group: %s does not contain any student";

    public static final String SHOW_MAILTO_LINK = "Showing the Email window";

    private final Group group;

    /**
     * Constructs a MailCommand with a predicate.
     */
    public MailCommand(Group group) {
        this.group = group;
    }

    /**
     * Generates a mailto link consisting of emails of students filtered accordingly
     * Shows a pop-up window containing the mailto link
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ReadOnlyAddressBook addressBook = model.getAddressBook();
        List<Person> personList = addressBook.getPersonList();

        List<Person> filteredPersonList = new ArrayList<>();
        for (Person person : personList) {
            if (person.hasGroup(group)) {
                filteredPersonList.add(person);
            }
        }

        if (filteredPersonList.size() == 0) {
            throw new CommandException(String.format(MESSAGE_NO_PERSON, group));
        }

        // Extract email addresses of filtered students
        List<String> emailList = filteredPersonList.stream()
                .map(Person::getEmail)
                .filter(email -> !email.value.isEmpty())
                .map(email -> email.value)
                .collect(Collectors.toList());

        String mailtoLink = "mailto:" + String.join(";", emailList);

        return new CommandResult(SHOW_MAILTO_LINK, mailtoLink);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MailCommand)) {
            return false;
        }

        MailCommand otherMailCommand = (MailCommand) other;
        return group.equals(otherMailCommand.group);
    }
}
