# Searching Web Elements by CSS 
[Link](https://github.com/it-acad/taqc-sprint-searching-by-css-Lokankara)

## Task 1

1. Open the LoginModalTest class.
2. In this task, selectors were used to locate elements. Analyze the changes compared to the previous implementation.
3. Implement the specified negative login scenarios on the page.

## Task 2

Prerequisite for completing this task: being logged in.
In this task you need to do the following:

1. Find the "Гуртки" tab using
2. Navigate to the 2nd page
3. Find and open the card "IT освіта: курси 'ГРАНД'"
4. Find and click the "Залишити коментар" button
5. Write a comment, rate it, and click the "Надіслати" button
6. Implement different possible negative scenarios.
7. For locating all elements, use selectors.

## Task 3
1. Additional task (optional)
2. Improved selectors if it possible and Parameterized Test Code for Logging In.

Let's generate invalid emails based on the given requirements using different testing techniques and then check how many characters the "email" field accepts using Equivalence Partitioning and Boundary Values Analysis.

 **Email Decision Tables**

| Conditions                     | T1   | T2   | T3   | T4   | T5   | T6   | T7   | T8   | T9   | T10  |
|--------------------------------|------|------|------|------|------|------|------|------|------|------|
| Contains Latin letters         | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  |
| Contains digits                | No   | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  |
| Contains '.', '@', '_' symbols | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  |
| Starts with a letter           | Yes  | Yes  | No   | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  |
| Dot before '@'                 | No   | No   | No   | Yes  | No   | No   | No   | No   | No   | No   |
| Domain contains letters only   | Yes  | Yes  | Yes  | Yes  | No   | Yes  | Yes  | Yes  | Yes  | Yes  |
| Host contains letters only     | Yes  | Yes  | Yes  | Yes  | Yes  | No   | Yes  | Yes  | Yes  | Yes  |
| Last domain at least 2 chars   | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | No   | Yes  | Yes  | Yes  |
| Last host at least 1 char      | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | No   | Yes  | Yes  |
| At least 2 domains             | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | No   | Yes  |
| Max 20 characters              | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | Yes  | No   |
| Result                         | Valid| Invalid|Invalid|Invalid|Invalid|Invalid|Invalid|Invalid|Invalid|Invalid|
