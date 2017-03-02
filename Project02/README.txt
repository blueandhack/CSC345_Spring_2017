Name: Yujia Lin

Quick View:

For the project, it will take an argument that is filename by user input, then open the file, and read every line.
For the file, every line is a command. We can write those file to use command create a BST.
In my program, we can use "insert, search, debug, delete, count, preOrder, inOrder" commands to operate the BST.
So, I complete all requirements, and my program works very well.

Notes:

When I write delete function at BinarySearchTree class, I made a mistake. 
I try to find minimum value from the left subtree, then replace the node.
When I try to check result, I find the mistake because it doesn't make sense, 
if the left subtree has big node than the parent node,
then the binary tree is not binary search tree. So, I should find max value, then I change it.