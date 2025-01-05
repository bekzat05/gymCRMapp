Feature: Trainer Service Map Component Tests
  This feature tests the TrainerServiceMap functionalities.

  Scenario: Find trainer by username
    Given a trainer exists with username "trainer1"
    When I search for trainer with username "trainer1"
    Then the trainer profile should be returned

  Scenario: Update trainer details
    Given a trainer exists with username "trainer1"
    When I update trainer "trainer1" with first name "John", last name "Doe", and specializations "Yoga", "Pilates"
    Then the details of trainer "trainer1" should be updated

  Scenario: Find all trainers
    When I fetch all trainers
    Then a list of trainers should be returned

  Scenario: Change active status of a trainer
    Given a trainer exists with username "trainer1"
    When I change the active status of trainer "trainer1" to "false"
    Then the active status of trainer "trainer1" should be "false"
