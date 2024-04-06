package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

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
public class MailTelegramCommand extends MailCommand {

    public static final String COMMAND_WORD = "mailtg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": generates email for students from "
            + "the specified group with the Telegram invite link.\n"
            + "Parameters: " + PREFIX_GROUP + "/[GROUP_NAME]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GROUP + "LAB10";

    public static final String MESSAGE_NO_GROUP_LINK = "Group %s does not contain a Telegram link.\n"
            + "Please add a link first using the " + EditGroupCommand.COMMAND_WORD + " command.";

    public static final String SHOW_MAILTO_LINK = "Showing the email window";

    private final Group group;

    /**
     * Constructs a MailCommand with a predicate.
     */
    public MailTelegramCommand(Group group) {
        super(group);
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

        if (!groupIsNotEmpty(model, group)) {
            throw new CommandException(String.format(MESSAGE_NO_PERSON, group));
        }

        List<Person> personList = addressBook.getPersonList();
        List<Person> filteredPersonList = new ArrayList<>();
        for (Person person : personList) {
            if (person.hasGroup(group)) {
                filteredPersonList.add(person);
            }
        }

        // Extract email addresses of filtered students
        List<String> emailList = filteredPersonList.stream()
                .map(Person::getEmail)
                .filter(email -> !email.value.isEmpty())
                .map(email -> email.value)
                .collect(Collectors.toList());

        String telegramLink = "";
        List<Group> groupList = addressBook.getGroupList();
        for (Group curr : groupList) {
            if (curr.isSameGroup(this.group)) {
                telegramLink = curr.telegramLink;
                break;
            }
        }

        if (telegramLink == "") {
            throw new CommandException(String.format(MESSAGE_NO_GROUP_LINK, group));
        }

        String mailtoLink = createMailtoUrl(String.join(";", emailList),
                String.format("Welcome to Group %s", group.groupName),
            String.format("Greetings,\n\nWelcome to Group %s.\n\n", group.groupName)
                    + String.format("Please join this Telegram group: %s.\n\n", telegramLink)
                    + String.format("Sent from TutorsContactPro."));

        return new CommandResult(SHOW_MAILTO_LINK, mailtoLink);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MailTelegramCommand)) {
            return false;
        }

        MailTelegramCommand otherMailCommand = (MailTelegramCommand) other;
        return group.equals(otherMailCommand.group);
    }
}
