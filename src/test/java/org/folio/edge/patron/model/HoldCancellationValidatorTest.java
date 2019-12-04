package org.folio.edge.patron.model;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HoldCancellationValidatorTest {

  @Test
  public void validateCancelHoldRequestNullObject() {
    String result = HoldCancellationValidator.validateCancelHoldRequest(null);
    assertEquals("invalid holdCancellationRequest", result);
  }

  @Test
  public void validateRequiredHoldMissingCancellationFields() {
    String expectedErrorMsg = "required fields for cancelling holds are missing (holdId, cancellationReasonId, canceledByUserId)";
    String cancellationJsonMissingHoldId = "{" +
      "\"canceledByUserId\" : \"" + UUID.randomUUID().toString() + "\"," +
      "\"cancellationReasonId\" : \"" + UUID.randomUUID().toString() + "\"," +
      "\"cancellationAdditionalInformation\" : \"blablabla\"" +
      "}";
    String result = HoldCancellationValidator.validateCancelHoldRequest(cancellationJsonMissingHoldId);
    assertEquals(expectedErrorMsg, result);

    String cancellationJsonMissingCanceledByUserId= "{" +
      "\"holdId\" :  \"" + UUID.randomUUID().toString() + "\"," +
      "\"canceledByUserId\" : \"\"," +
      "\"cancellationReasonId\" : \"" + UUID.randomUUID().toString() + "\"," +
      "\"cancellationAdditionalInformation\" : \"blablabla\"" +
      "}";
    result = HoldCancellationValidator.validateCancelHoldRequest(cancellationJsonMissingCanceledByUserId);
    assertEquals(expectedErrorMsg, result);

    String cancellationJsonMissingcancellationReasonId= "{" +
      "\"holdId\" :  \"" + UUID.randomUUID().toString() + "\"," +
      "\"canceledByUserId\" : \"" + UUID.randomUUID().toString() + "\"," +
      "\"cancellationAdditionalInformation\" : \"blablabla\"" +
      "}";
    result = HoldCancellationValidator.validateCancelHoldRequest(cancellationJsonMissingcancellationReasonId);
    assertEquals(expectedErrorMsg, result);
  }

  @Test
  public void validateCancelHoldInvalidUUIDs() {
    String validUUID = "3a40852d-49fd-4df2-a1f9-6e2641a6e91f";
    String invalidUUID = "3a40852d-g9fd-fdf2-a1f9-6e2641a6e91p";
    String expectedErrorMsg = "invalid values for one of the required fields (holdId, cancellationReasonId, canceledByUserId)";

    String invalidHoldId = "{" +
    "\"holdId\" : \"" + invalidUUID + "\"," +
    "\"canceledByUserId\" : \"" + validUUID + "\"," +
      "\"cancellationReasonId\" : \"" + validUUID + "\"," +
    "\"cancellationAdditionalInformation\" : \"blablabla\"" +
    "}";
    String result = HoldCancellationValidator.validateCancelHoldRequest(invalidHoldId);
    assertEquals(expectedErrorMsg, result);

    String invalidCanceledByUserId = "{" +
      "\"holdId\" : \"" + validUUID + "\"," +
      "\"canceledByUserId\" : \"" + invalidUUID + "\"," +
      "\"cancellationReasonId\" : \"" + validUUID + "\"," +
      "\"cancellationAdditionalInformation\" : \"blablabla\"" +
      "}";
    result = HoldCancellationValidator.validateCancelHoldRequest(invalidCanceledByUserId);
    assertEquals(expectedErrorMsg, result);

    String invalidCancellationReasonId = "{" +
      "\"holdId\" : \"" + validUUID + "\"," +
      "\"canceledByUserId\" : \"" + validUUID + "\"," +
      "\"cancellationReasonId\" : \"" + invalidUUID + "\"," +
      "\"cancellationAdditionalInformation\" : \"blablabla\"" +
      "}";
    result = HoldCancellationValidator.validateCancelHoldRequest(invalidCancellationReasonId);
    assertEquals(expectedErrorMsg, result);
  }

  @Test
  public void validateCancelHoldRequestValidParams() {
    String cancellationJson = "{" +
      "\"holdId\" : \"" + UUID.randomUUID().toString() + "\"," +
      "\"canceledByUserId\" : \"" + UUID.randomUUID().toString() + "\"," +
      "\"cancellationReasonId\" : \"" + UUID.randomUUID().toString() + "\"," +
      "\"cancellationAdditionalInformation\" : \"blablabla\"," +
      "\"canceledDate\" : \"2019-09-19T19:47:12.508+0000\"" +
      "}";
    String result = HoldCancellationValidator.validateCancelHoldRequest(cancellationJson);
    assertNull(result);
  }
}
