# The first line of this file starts with the keyword Feature: followed by a name.
# It’s a good idea to use a name similar to the file name.
#
#The second line is a brief description of the feature.
# Cucumber does not execute this line because it’s documentation.
#
#The fourth line, Scenario: Sunday is not Friday is a scenario,
# which is a concrete example illustrating how the software should behave.
#
#The last three lines starting with Given, When and Then are the steps of our scenario.
# This is what Cucumber will execute.
Feature: Is it Friday yet?
  Everybody wants to know when it's Friday

  Scenario: Sunday isn't Friday
    Given today is Sunday
    When I ask whether it's Friday yet
    Then I should be told "Nope"