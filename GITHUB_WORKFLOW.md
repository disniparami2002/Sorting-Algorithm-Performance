# GitHub from start to finish (two members)

## Repository owner
1. Sign in to GitHub and click **New repository**.
2. Name it `sorting-algorithm-performance`.
3. Choose Public or Private as required by your assignment.
4. Create the repository.
5. Add the second member under repository access/collaborators.
6. In the project folder terminal:

```bash
git init
git add .
git commit -m "Initial project setup"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/sorting-algorithm-performance.git
git push -u origin main
```

## Member 2
After accepting the collaborator invitation:

```bash
git clone https://github.com/YOUR-USERNAME/sorting-algorithm-performance.git
cd sorting-algorithm-performance
git checkout -b member2-sorting-tests
```

Make changes, then:

```bash
git add .
git commit -m "Add sorting algorithm tests"
git push -u origin member2-sorting-tests
```

Open a Pull Request on GitHub and ask Member 1 to review it.

## Member 1
Create a branch for GUI/CSV changes:

```bash
git checkout -b member1-gui-csv
```

Commit and push changes:

```bash
git add .
git commit -m "Improve CSV upload and GUI"
git push -u origin member1-gui-csv
```

Open a Pull Request and ask Member 2 to review it.

## Keep branches updated
```bash
git checkout main
git pull origin main
git checkout YOUR-BRANCH
git merge main
```

If a merge conflict appears, open the marked files, choose the correct combined code, save, then:

```bash
git add .
git commit -m "Resolve merge conflicts"
git push
```

Do not share GitHub passwords or access tokens. Use GitHub's browser sign-in or credential manager.
