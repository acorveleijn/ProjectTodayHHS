package nl.hhs.apep2122group1;

import org.junit.Assert;
import org.junit.Test;

import nl.hhs.apep2122group1.models.Label;
import nl.hhs.apep2122group1.utils.ValidationResult;
import nl.hhs.apep2122group1.utils.Validators;

/**
 * These tests verify whether the validation methods pass or fail correctly for all sorts of
 * given input. Some are written from user perspective, some are written from developer perspective
 * as they may be used to prevent the database from containing invalid, confusing or aesthetically
 * unpleasing data such as a label with an empty title, a username containing spaces, or a name
 * starting with whitespace.
 */
@SuppressWarnings("ConstantConditions")
public class ValidatorsTests {
    @Test
    public void validateStringNotNullOrEmpty_null_and_empty_strings_fail() {
        Assert.assertFalse(Validators.validateStringNotNullOrEmpty(null));
        Assert.assertFalse(Validators.validateStringNotNullOrEmpty(""));
    }

    @Test
    public void validateStringNotNullOrEmpty_not_empty_strings_pass() {
        Assert.assertTrue(Validators.validateStringNotNullOrEmpty(" "));
        Assert.assertTrue(Validators.validateStringNotNullOrEmpty("Not Empty"));
    }

    @Test
    public void validatePasswordComplexity_too_short_passwords_fail() {
        Assert.assertEquals(Validators.validatePasswordComplexity(""), ValidationResult.TOO_SHORT);
        Assert.assertEquals(Validators.validatePasswordComplexity("short"), ValidationResult.TOO_SHORT);
    }

    @Test
    public void validatePasswordComplexity_same_char_passwords_fail() {
        Assert.assertEquals(Validators.validatePasswordComplexity("111111"), ValidationResult.SAME_CHARACTERS);
        Assert.assertEquals(Validators.validatePasswordComplexity("aaaaaaaaaa"), ValidationResult.SAME_CHARACTERS);
    }

    @Test
    public void validatePasswordComplexity_known_easy_to_guess_passwords_fail() {
        Assert.assertEquals(Validators.validatePasswordComplexity("password"), ValidationResult.SPECIFIC_INPUT_NOT_ALLOWED);
        Assert.assertEquals(Validators.validatePasswordComplexity("123456"), ValidationResult.SPECIFIC_INPUT_NOT_ALLOWED);
        Assert.assertEquals(Validators.validatePasswordComplexity("batman"), ValidationResult.SPECIFIC_INPUT_NOT_ALLOWED);
    }

    @Test
    public void validatePasswordComplexity_valid_password_examples_pass() {
        Assert.assertEquals(Validators.validatePasswordComplexity("v3ry c0mpl3x"), ValidationResult.OK);
        Assert.assertEquals(Validators.validatePasswordComplexity("superman"), ValidationResult.OK);
    }

    @Test
    public void validateStringDoesNotContainWhitespace_strings_with_whitespace_fail() {
        Assert.assertFalse(Validators.validateStringDoesNotContainWhitespace("Yes Whitespace"));
        Assert.assertFalse(Validators.validateStringDoesNotContainWhitespace(" YesWhitespace"));
        Assert.assertFalse(Validators.validateStringDoesNotContainWhitespace("Yes\tWhitespace"));
        Assert.assertFalse(Validators.validateStringDoesNotContainWhitespace("\nYesWhitespace\n"));
        Assert.assertFalse(Validators.validateStringDoesNotContainWhitespace("\tYesWhitespace"));
        Assert.assertFalse(Validators.validateStringDoesNotContainWhitespace(" "));
    }

    @Test
    public void validateStringDoesNotContainWhitespace_strings_with_whitespace_pass() {
        Assert.assertTrue(Validators.validateStringDoesNotContainWhitespace("NoWhitespace"));
        Assert.assertTrue(Validators.validateStringDoesNotContainWhitespace(""));
    }

    @Test
    public void validateNewLabelTitleUnique_non_unique_label_titles_fail() {
        Label[] labels = getLabels();

        Assert.assertFalse(Validators.validateNewLabelTitleUnique("school", labels));
        Assert.assertFalse(Validators.validateNewLabelTitleUnique("PETS", labels));
    }

    @Test
    public void validateNewLabelTitleUnique_unique_label_title_passes() {
        Label[] labels = getLabels();

        Assert.assertTrue(Validators.validateNewLabelTitleUnique("Not In The List", labels));
    }

    @Test
    public void validateEditLabelTitleUnique_non_unique_label_title_change_fail() {
        Label[] labels = getLabels();

        Assert.assertFalse(Validators.validateEditLabelTitleUnique("School", "Work", labels));
        Assert.assertFalse(Validators.validateEditLabelTitleUnique("Groceries", "PETS", labels));
    }

    @Test
    public void validateEditLabelTitleUniquelabel_title_change_to_same_pass() {
        Label[] labels = getLabels();

        Assert.assertTrue(Validators.validateEditLabelTitleUnique("School", "School", labels));
        Assert.assertTrue(Validators.validateEditLabelTitleUnique("School", "SCHOOL", labels));
    }

    @Test
    public void validateEditLabelTitleUniquelabel_title_change_to_unique_pass() {
        Label[] labels = getLabels();

        Assert.assertTrue(Validators.validateEditLabelTitleUnique("School", "Unique", labels));
    }

    @Test
    public void validateDateIsEmptyOrNotNull_null_and_empty_strings_pass(){
        Assert.assertTrue(Validators.validateDateIsEmptyOrNotNull(""));
        Assert.assertTrue(Validators.validateDateIsEmptyOrNotNull("2022-06-14 14:10"));
    }

    @Test
    public void validateDateIsEmptyOrNotNull_not_null_or_empty_strings_fail(){
        Assert.assertFalse(Validators.validateDateIsEmptyOrNotNull("niet leeg"));
        Assert.assertFalse(Validators.validateDateIsEmptyOrNotNull("2022-06-11"));
    }

    private Label[] getLabels() {
        Label label1 = new Label("School", "#123456", "AvengersFanboy");
        Label label2 = new Label("Work", "#123456", "AvengersFanboy");
        Label label3 = new Label("Groceries", "#123456", "AvengersFanboy");
        Label label4 = new Label("Pets", "#123456", "AvengersFanboy");
        Label label5 = new Label("Knitting club", "#123456", "AvengersFanboy");

        return new Label[] { label1, label2, label3, label4, label5 };
    }
}