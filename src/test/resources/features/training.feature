Feature: Training Service

  Scenario: Adding a new training
    Given a new training with trainee ID "1", trainer ID "3" and training type ID "1" and date "2022-03-15T10:00:00" and trainingDuration "1"
    When I send a request to create the training
    Then the training should be created with ID "6"
    And the trainer's workload should be updated in the reporting microservice
