package nl.hhs.apep2122group1;

import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

import nl.hhs.apep2122group1.models.Task;

public class TaskComparisonTests {

    @Test
    public void compareTo_toDoTasks_ascending_sorts_correctly() {
        // ARRANGE:
        Task baseTask = new Task(
                "Buy birthday present",
                LocalDateTime.of(2022, 5, 10, 14, 30),
                "Test description", "testUser",  2);

        Task comparisonTask = new Task(
                "Buy bird food",
                LocalDateTime.of(2022, 6, 10, 14, 30),
                "Test description", "testUser",  2);

        // ACT:
        int result = baseTask.compareTo(comparisonTask);

        // ASSERT:
        Assert.assertTrue(result < 0);
    }

    @Test
    public void compareTo_toDoTasks_descending_sorts_correctly() {
        // ARRANGE:
        Task baseTask = new Task(
                "Prepare assessment",
                LocalDateTime.of(2022, 6, 20, 20, 30),
                "Test description", "testUser",  2);

        Task comparisonTask = new Task(
                "Submit project",
                LocalDateTime.of(2022, 6, 17, 23, 59),
                "Test description", "testUser",  2);

        // ACT:
        int result = baseTask.compareTo(comparisonTask);

        // ASSERT:
        Assert.assertTrue(result > 0);
    }

    @Test
    public void compareTo_toDoTasks_equal_sorts_correctly() {
        // ARRANGE:
        Task baseTask = new Task(
                "Prepare assessment",
                LocalDateTime.of(2022, 6, 17, 23, 59),
                "Test description", "testUser",  2);

        Task comparisonTask = new Task(
                "Submit project",
                LocalDateTime.of(2022, 6, 17, 23, 59),
                "Test description", "testUser",  2);

        // ACT:
        int result = baseTask.compareTo(comparisonTask);

        // ASSERT:
        Assert.assertEquals(0, result);
    }

    @Test
    public void compareTo_toDoTasks_base_null_sorts_correctly() {
        // ARRANGE:
        Task baseTask = new Task(
                "Buy bird food",
                null,
                "Test description", "testUser",  2);

        Task comparisonTask = new Task(
                "Submit project",
                LocalDateTime.of(2022, 6, 17, 23, 59),
                "Test description", "testUser",  2);

        // ACT:
        int result = baseTask.compareTo(comparisonTask);

        // ASSERT:
        Assert.assertEquals(0, result);
    }

    @Test
    public void compareTo_toDoTasks_comparison_null_sorts_correctly() {
        // ARRANGE:
        Task baseTask = new Task(
                "Buy birthday present",
                LocalDateTime.of(2022, 5, 10, 14, 30),
                "Test description", "testUser",  1);

        Task comparisonTask = new Task(
                "Buy bird food",
                null,
                "Test description", "testUser",  7);

        // ACT:
        int result = baseTask.compareTo(comparisonTask);

        // ASSERT:
        Assert.assertEquals(0, result);
    }

    @Test
    public void compareTo_toDoTasks_both_null_sorts_correctly() {
        // ARRANGE:
        Task baseTask = new Task(
                "Buy birthday present",
                null,
                "Test description", "testUser",  1);

        Task comparisonTask = new Task(
                "Buy bird food",
                null,
                "Test description", "testUser",  7);

        // ACT:
        int result = baseTask.compareTo(comparisonTask);

        // ASSERT:
        Assert.assertEquals(0, result);
    }

    @Test
    public void compareTo_doneTasks_ascending_sorts_correctly() {
        // ARRANGE:
        Task baseTask = new Task(
                "Cancel subscription",
                LocalDateTime.of(2022, 7, 10, 14, 30),
                "Test description", "testUser",  1);
        baseTask.setCompleted(LocalDateTime.of(2022, 5, 8, 11, 15));

        Task comparisonTask = new Task(
                "Buy bird food",
                LocalDateTime.of(2022, 6, 10, 14, 30),
                "Test description", "testUser",  2);
        comparisonTask.setCompleted(LocalDateTime.of(2022, 6, 8, 11, 20));

        // ACT:
        int result = baseTask.compareTo(comparisonTask);

        // ASSERT:
        Assert.assertTrue(result < 0);
    }

    @Test
    public void compareTo_doneTasks_descending_sorts_correctly() {
        // ARRANGE:
        Task baseTask = new Task(
                "Buy bird food",
                LocalDateTime.of(2022, 6, 10, 14, 30),
                "Test description", "testUser",  2);
        baseTask.setCompleted(LocalDateTime.of(2022, 6, 8, 11, 20));

        Task comparisonTask = new Task(
                "Cancel subscription",
                LocalDateTime.of(2022, 7, 10, 14, 30),
                "Test description", "testUser",  1);
        comparisonTask.setCompleted(LocalDateTime.of(2022, 5, 8, 11, 15));

        // ACT:
        int result = baseTask.compareTo(comparisonTask);

        // ASSERT:
        Assert.assertTrue(result > 0);
    }

    @Test
    public void compareTo_doneTasks_equal_sorts_correctly() {
        // ARRANGE:
        Task baseTask = new Task(
                "Buy bird food",
                null,
                "Test description", "testUser",  2);
        baseTask.setCompleted(LocalDateTime.of(2022, 6, 8, 11, 20));

        Task comparisonTask = new Task(
                "Cancel subscription",
                LocalDateTime.of(2022, 7, 10, 14, 30),
                "Test description", "testUser",  1);
        comparisonTask.setCompleted(LocalDateTime.of(2022, 6, 8, 11, 20));

        // ACT:
        int result = baseTask.compareTo(comparisonTask);

        // ASSERT:
        Assert.assertEquals(0, result);
    }

    @Test
    public void compareTo_null_throws_exception() {
        // ARRANGE:
        Task baseTask = new Task(
                "Buy birthday present",
                LocalDateTime.of(2022, 5, 10, 14, 30),
                "Test description", "testUser",  2);
        // ACT:

        // ASSERT:
        assertThrows(NullPointerException.class, () -> baseTask.compareTo(null));
    }
}
