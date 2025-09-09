import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

export function middleware(request: NextRequest) {
  const token = request.cookies.get('token');
  const { pathname } = request.nextUrl;

  // Public routes (no login required)
  const publicPaths = ['/auth/login', '/auth/register', '/'];

  // If user is logged in and tries to access login/register → send to home
  if (token && publicPaths.includes(pathname)) {
    return NextResponse.redirect(new URL('/home', request.url));
  }

  // If user is not logged in and tries to access a protected route → send to login
  if (!token && !publicPaths.includes(pathname)) {
    return NextResponse.redirect(new URL('/auth/login', request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: [
    '/((?!api|_next/static|_next/image|favicon.ico).*)',
  ],
};
