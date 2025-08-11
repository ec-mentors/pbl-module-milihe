document.addEventListener('DOMContentLoaded', () => {
    const recipeGridContainer = document.getElementById('recipe-grid-container');
    const storedIngredientsJson = localStorage.getItem('userIngredients');

    if (!storedIngredientsJson) {
        recipeGridContainer.innerHTML = '<h2>Could not find any ingredients. Please go back.</h2>';
        return;
    }

    const ingredients = JSON.parse(storedIngredientsJson);

    async function findAndDisplayRecipes() {
        recipeGridContainer.innerHTML = '<h2>Finding recipes for you...</h2>';
        try {
            const response = await fetch('http://127.0.0.1:8080/api/recipes/find', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(ingredients),
            });
            if (!response.ok) { throw new Error(`HTTP error! Status: ${response.status}`); }
            const recipeSuggestions = await response.json();
            renderRecipeCards(recipeSuggestions);
        } catch (error) {
            console.error("Error fetching recipes:", error);
            recipeGridContainer.innerHTML = '<h2>Sorry, something went wrong.</h2>';
        }
    }

    function renderRecipeCards(recipes) {
        recipeGridContainer.innerHTML = '';
        if (recipes.length === 0) {
            recipeGridContainer.innerHTML = '<h2>No recipes found. Try different ingredients!</h2>';
            return;
        }
        recipes.forEach(recipe => {
            const card = document.createElement('div');
            card.className = 'recipe-card';
            const missingInfoClass = recipe.missingIngredientCount === 0 ? 'zero' : 'some';
            const missingText = recipe.missingIngredientCount === 0
                ? 'You have all ingredients!'
                : `Missing: ${recipe.missingIngredientCount} ingredient(s)`;

            card.innerHTML = `
                <img src="${recipe.image || 'https://placehold.co/600x400?text=No+Image'}" alt="${recipe.title}">
                <div class="recipe-card-content">
                    <div>
                        <h3>${recipe.title}</h3>
                        <p class="missing-info ${missingInfoClass}">${missingText}</p>
                    </div>
                    <button class="btn btn-primary">View Recipe</button>
                </div>
            `;

            const viewRecipeBtn = card.querySelector('.btn-primary');
            viewRecipeBtn.addEventListener('click', () => {
                window.location.href = `recipe-detail.html?id=${recipe.id}`;
            });

            recipeGridContainer.appendChild(card);
        });
    }

    findAndDisplayRecipes();
});