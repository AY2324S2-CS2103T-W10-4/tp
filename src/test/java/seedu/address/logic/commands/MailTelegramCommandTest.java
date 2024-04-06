package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_LINK;
import static seedu.address.logic.commands.CommandTestUtil.assertMailCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MailTelegramCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Group group1 = new Group("TUT01");
        Group group2 = new Group("TUT02");

        MailTelegramCommand findFirstCommand = new MailTelegramCommand(group1);
        MailTelegramCommand findSecondCommand = new MailTelegramCommand(group2);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        MailTelegramCommand findFirstCommandCopy = new MailTelegramCommand(group1);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_withMultiplePredicate_success() {
        model.addGroup(new Group("TUT01", VALID_TELEGRAM_LINK));
        List<Person> personList = Arrays.asList(
                new PersonBuilder().withName("Alice").withEmail("test1@example.com").withGroups("TUT01").build(),
                new PersonBuilder().withName("Bob").withEmail("test2@example.com").withGroups("TUT01").build()
        );
        model.addPerson(personList.get(0));
        model.addPerson(personList.get(1));

        MailTelegramCommand mailCommand = new MailTelegramCommand(new Group("TUT01"));
        assertMailCommandSuccess(mailCommand, model, MailTelegramCommand.SHOW_MAILTO_LINK, model);
    }
}
