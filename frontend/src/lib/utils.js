export function formatDate(date) {
  return date.toLocaleDateString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
  });
}

export function isAuthenticated() {
  const token = localStorage.getItem("token");
  return !!token;
}