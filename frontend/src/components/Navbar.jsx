import React from 'react';
import { Link } from 'react-router-dom';
import { Hotel, LayoutDashboard, PlusCircle } from 'lucide-react';

const Navbar = () => {
    return (
        <nav className="glass-panel sticky top-0 z-50 mb-8">
            <div className="container" style={{ padding: '1rem 2rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <Link to="/" style={{ textDecoration: 'none', color: 'white', display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                    <div style={{ background: 'var(--primary)', padding: '0.5rem', borderRadius: '0.5rem' }}>
                        <Hotel size={24} />
                    </div>
                    <span style={{ fontSize: '1.25rem', fontWeight: 'bold' }}>HotelAdmin</span>
                </Link>

                <div style={{ display: 'flex', gap: '1.5rem' }}>
                    <Link to="/" className="btn" style={{ color: 'var(--text-muted)', background: 'transparent' }}>
                        <LayoutDashboard size={20} />
                        Dashboard
                    </Link>
                    <Link to="/create" className="btn btn-primary">
                        <PlusCircle size={20} />
                        New Reservation
                    </Link>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
