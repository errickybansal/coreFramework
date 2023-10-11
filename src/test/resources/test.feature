Feature: Test cucumber test

  Scenario: Verify Env builder
    Given Base is configured with env "sit" and context "default"
    When Fetched env property "service.test"
    Then Verify the url property is not null

  Scenario: Verify Env builder negative sceanrio
    Given Base is configured with env "sit" and context "default"
    When Fetched env property "service.notest"
    Then Verify the url property is null