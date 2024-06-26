package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGroups.LAB10;
import static seedu.address.testutil.TypicalGroups.TUT04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;

public class UniqueGroupListTest {

    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.contains(null));
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(uniqueGroupList.contains(TUT04));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        uniqueGroupList.add(TUT04);
        assertTrue(uniqueGroupList.contains(TUT04));
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.add(null));
    }

    @Test
    public void add_duplicateGroup_throwsDuplicateGroupException() {
        uniqueGroupList.add(TUT04);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.add(TUT04));
    }

    @Test
    public void setGroup_nullTargetGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup(null, TUT04));
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup(TUT04, null));
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> uniqueGroupList.setGroup(TUT04, TUT04));
    }

    @Test
    public void setGroup_editedGroupIsSameGroup_success() {
        uniqueGroupList.add(TUT04);
        uniqueGroupList.setGroup(TUT04, TUT04);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(TUT04);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasDifferentIdentity_success() {
        uniqueGroupList.add(TUT04);
        uniqueGroupList.setGroup(TUT04, LAB10);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(LAB10);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasNonUniqueIdentity_throwsDuplicateGroupException() {
        uniqueGroupList.add(TUT04);
        uniqueGroupList.add(LAB10);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.setGroup(TUT04, LAB10));
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.remove(null));
    }

    @Test
    public void remove_groupDoesNotExist_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> uniqueGroupList.remove(TUT04));
    }

    @Test
    public void remove_existingGroup_removesGroup() {
        uniqueGroupList.add(TUT04);
        uniqueGroupList.remove(TUT04);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullUniqueGroupList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroups((UniqueGroupList) null));
    }

    @Test
    public void setGroups_uniqueGroupList_replacesOwnListWithProvidedUniqueGroupList() {
        uniqueGroupList.add(TUT04);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(LAB10);
        uniqueGroupList.setGroups(expectedUniqueGroupList);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroups((List<Group>) null));
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        uniqueGroupList.add(TUT04);
        List<Group> groupList = Collections.singletonList(LAB10);
        uniqueGroupList.setGroups(groupList);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(LAB10);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Group> listWithDuplicateGroups = Arrays.asList(TUT04, TUT04);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.setGroups(listWithDuplicateGroups));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueGroupList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        UniqueGroupList uniqueGroupListCopy = new UniqueGroupList();
        uniqueGroupListCopy.add(TUT04);
        uniqueGroupList.add(TUT04);
        assertTrue(uniqueGroupList.equals(uniqueGroupListCopy));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueGroupList.asUnmodifiableObservableList().toString(), uniqueGroupList.toString());
    }

    @Test
    public void hashCode_equalObjectsSameHashCode() {
        Group group1 = new Group("TUT01");
        Group group2 = new Group("TUT01");

        UniqueGroupList list1 = new UniqueGroupList();
        list1.add(group1);

        UniqueGroupList list2 = new UniqueGroupList();
        list2.add(group2);

        assertEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    public void hashCode_diffObjectsDiffHashCode() {
        Group group1 = new Group("TUT01");
        Group group2 = new Group("TUT02");

        UniqueGroupList list1 = new UniqueGroupList();
        list1.add(group1);

        UniqueGroupList list2 = new UniqueGroupList();
        list2.add(group2);

        assertNotEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    public void iterator_iterationOverElements_returnsAllElements() {
        // Create a list of groups
        List<Group> groupList = new ArrayList<>();
        groupList.add(new Group("TUT01"));
        groupList.add(new Group("TUT02"));
        groupList.add(new Group("TUT03"));

        // Create a UniqueGroupList
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        uniqueGroupList.setGroups(groupList);

        // Iterate over the elements using iterator
        Iterator<Group> iterator = uniqueGroupList.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Group nextGroup = iterator.next();
            // Ensure that the next element returned by the iterator is from the original list
            assertEquals(groupList.get(count), nextGroup);
            count++;
        }

        // Ensure that the iterator traverses all elements
        assertEquals(groupList.size(), count);
    }

    @Test
    public void iterator_iterationOverEmptyList_returnsEmptyIterator() {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        Iterator<Group> iterator = uniqueGroupList.iterator();
        assertFalse(iterator.hasNext());
    }
}
