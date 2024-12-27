import Header from './components/Header.jsx';
import RecipeList from './components/RecipeList.jsx';
import RecipeTextSearch from './components/RecipeTextSearch.jsx';
import './App.css'

function App() {
  return (
    <div className='App'>
      <Header />
      <RecipeList />
      <RecipeTextSearch />
    </div>
  )
}

export default App
